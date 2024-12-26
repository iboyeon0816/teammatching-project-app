package com.sphere.demo.service.project;


import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.*;
import com.sphere.demo.domain.*;
import com.sphere.demo.domain.enums.ApplicationState;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectPosition;
import com.sphere.demo.exception.ex.*;
import com.sphere.demo.repository.*;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ApplyDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandService {

    @Value("${file.dir}")
    private String FILE_DIR;

    private final PlatformRepository platformRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final ResumeSnapshotRepository resumeSnapshotRepository;
    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectRecruitPositionRepository projectPositionRepository;

    public Project create(Long userId, ProjectDetailDto createDto) {
        Project project = ProjectConverter.toProject(createDto);

        setAssociations(createDto, project);
        setUserToProject(userId, project);

        return projectRepository.save(project);
    }

    public void uploadImage(Long userId, Long projectId, MultipartFile file) {
        Project project = validateAndFetchProject(userId, projectId);

        validateImageFile(file);
        String fileName = saveFile(file);

        project.setImagePath(fileName);
    }

    public void update(Long userId, Long projectId, ProjectDetailDto updateDto) {
        Project project = validateAndFetchProject(userId, projectId);
        project.clearAssociations();

        project.update(updateDto);
        setAssociations(updateDto, project);
    }

    public void updateImage(Long userId, Long projectId, MultipartFile file) {
        Project project = validateAndFetchProject(userId, projectId);

        validateImageFile(file);
        deleteFile(project.getImagePath());
        String newFileName = saveFile(file);

        project.setImagePath(newFileName);
    }

    public void delete(Long userId, Long projectId) {
        Project project = validateAndFetchProject(userId, projectId);

        deleteFile(project.getImagePath());
        projectRepository.delete(project);
    }

    public ProjectApplication apply(Long userId, Long projectPositionId, ApplyDto applyDto) {
        User user = fetchUser(userId);
        ProjectPosition projectPosition = validateAndFetchProjectPosition(user, projectPositionId);
        ResumeSnapshot resumeSnapshot = createAndSaveResumeSnapshot(user, applyDto);

        ProjectApplication projectApplication = ProjectApplicationConverter.toProjectApplication(resumeSnapshot);
        projectApplication.setProjectPosition(projectPosition);
        projectApplication.setUser(user);

        return projectApplicationRepository.save(projectApplication);
    }

    public void projectViewUp(Project project) {
        project.viewUp();
    }

    private Project validateAndFetchProject(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        if (!Objects.equals(project.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return project;
    }

    private static void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ProjectException(ErrorStatus.EMPTY_FILE);
        }

        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new ProjectException(ErrorStatus.INVALID_CONTENT_TYPE);
        }
    }

    private String saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(FILE_DIR + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ProjectException(ErrorStatus.FILE_UPLOAD_FAILED);
        }
        return fileName;
    }

    private void deleteFile(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(FILE_DIR + imagePath);
            if (file.exists()) {
                if (!file.delete()) {
                    log.info("[ProjectCommandService][delete] Project image is not deleted: {}", imagePath);
                }
            }
        }
    }

    private void setAssociations(ProjectDetailDto projectDetailDto, Project project) {
        setPlatformToProject(projectDetailDto, project);
        setTechnologyToProject(projectDetailDto, project);
        setPositionToProject(projectDetailDto, project);
    }

    private void setPlatformToProject(ProjectDetailDto projectDetailDto, Project project) {
        List<Platform> platformList = projectDetailDto.getPlatformIdList().stream().map(
                platformId -> platformRepository.findById(platformId)
                        .orElseThrow(() -> new PlatformException(ErrorStatus.PLATFORM_NOT_FOUND))
        ).toList();

        List<ProjectPlatform> projectPlatformList = ProjectPlatformConverter.toProjectPlatform(platformList);
        projectPlatformList.forEach(projectPlatform -> projectPlatform.setProject(project));
    }


    private void setTechnologyToProject(ProjectDetailDto projectDetailDto, Project project) {
        List<ProjectTechnology> projectTechnologyList = projectDetailDto.getTechnologyNameList().stream().map(
                ProjectTechnology::new
        ).toList();

        projectTechnologyList.forEach(technology -> technology.setProject(project));
    }

    private void setPositionToProject(ProjectDetailDto projectDetailDto, Project project) {
        List<PositionContext> positionContextList = projectDetailDto.getPositionDtoList().stream().map(
                positionDto -> new PositionContext(
                        positionRepository.findById(positionDto.getPositionId())
                                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND)),
                        positionDto.getMemberCount()
                )
        ).toList();

        List<ProjectPosition> projectPositionList = ProjectPositionConverter.toProjectPositionList(positionContextList);
        projectPositionList.forEach(projectPosition -> projectPosition.setProject(project));
    }

    private void setUserToProject(Long userId, Project project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        project.setUser(user);
    }

    private ProjectPosition validateAndFetchProjectPosition(User user, Long projectPositionId) {
        ProjectPosition projectPosition = projectPositionRepository.findById(projectPositionId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_POSITION_NOT_FOUND));

        Project project = projectPosition.getProject();
        if (project.getStatus().equals(ProjectState.COMPLETED)) {
            throw new ProjectException(ErrorStatus.ALREADY_COMPLETED_PROJECT);
        }

        Integer memberCount = projectPosition.getMemberCount();
        Integer approvedCount = 0;
        List<ProjectApplication> applicationList = projectPosition.getProjectApplicationList();
        for (ProjectApplication projectApplication : applicationList) {
            if (projectApplication.getState().equals(ApplicationState.APPROVED)) {
                approvedCount++;
            }

            if (projectApplication.getUser().getId().equals(user.getId())) {
                throw new UserException(ErrorStatus.ALREADY_APPLIED_USER);
            }
        }

        if (Objects.equals(memberCount, approvedCount)) {
            throw new ProjectException(ErrorStatus.ALREADY_MATCHING_END_POSITION);
        }

        User owner = project.getUser();
        if (owner.getId().equals(user.getId())) {
            throw new UserException(ErrorStatus.OWN_PROJECT);
        }

        return projectPosition;
    }

    private User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    private ResumeSnapshot createAndSaveResumeSnapshot(User user, ApplyDto applyDto) {
        Resume resume = validateAndFetchResume(user, applyDto);
        ResumeSnapshot resumeSnapshot = ResumeConverter.toResumeSnapshot(resume);

        List<ResumeSnapshotTechnology> resumeSnapshotTechnologies = resume.getResumeTechnologySet().stream()
                .map(ResumeConverter::toResumeSnapshotTechnology)
                .toList();
        resumeSnapshotTechnologies.forEach(resumeSnapshotTechnology -> resumeSnapshotTechnology.setResumeSnapshot(resumeSnapshot));

        resumeSnapshotRepository.save(resumeSnapshot);

        return resumeSnapshot;
    }

    private Resume validateAndFetchResume(User user, ApplyDto applyDto) {
        Resume resume = resumeRepository.findById(applyDto.getResumeId())
                .orElseThrow(() -> new ResumeException(ErrorStatus.RESUME_NOT_FOUND));

        if (!resume.getUser().getId().equals(user.getId())){
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return resume;
    }

    @Getter
    @AllArgsConstructor
    public static class PositionContext {
        private Position position;
        private Integer memberCount;
    }
}
