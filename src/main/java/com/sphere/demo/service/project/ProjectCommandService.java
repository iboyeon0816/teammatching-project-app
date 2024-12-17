package com.sphere.demo.service.project;


import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.converter.project.ProjectMatchConverter;
import com.sphere.demo.converter.project.ProjectPlatformConverter;
import com.sphere.demo.converter.project.ProjectPositionConverter;
import com.sphere.demo.domain.*;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.exception.ex.PlatformException;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.exception.ex.UserException;
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
    private final ProjectRepository projectRepository;
    private final ProjectMatchRepository projectMatchRepository;
    private final ProjectRecruitPositionRepository projectPositionRepository;

    public Project create(Long userId, ProjectDetailDto createDto) {
        Project project = ProjectConverter.toProject(createDto);
        mapToProject(createDto, project);
        setUserToProject(userId, project);
        return projectRepository.save(project);
    }

    public void uploadImage(Long userId, Long projectId, MultipartFile file) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        validateUserAuth(userId, project);

        validateFile(file);
        String fileName = saveImage(file);
        project.setImagePath(fileName);
    }

    public void update(Long userId, Long projectId, ProjectDetailDto updateDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        validateUserAuth(userId, project);

        project.update(updateDto);
        project.getTechnologySet().clear();
        project.getProjectRecruitPositionSet().clear();
        project.getProjectPlatformSet().clear();
        mapToProject(updateDto, project);
    }

    public void delete(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        validateUserAuth(userId, project);

        String imagePath = project.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            deleteImage(imagePath);
        }
        projectRepository.delete(project);
    }

    public void apply(Long userId, Long projectId, ApplyDto applyDto) {
        ProjectRecruitPosition projectPosition = getProjectPosition(userId, projectId, applyDto);
        validateMatchingNotCompleted(projectPosition);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        validateAlreadyApplied(user, projectPosition);

        ProjectMatch projectMatch = ProjectMatchConverter.toProjectMatch(user, projectPosition);
        projectMatchRepository.save(projectMatch);
    }

    public void projectViewUp(Project project) {
        project.viewUp();
    }

    private static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ProjectException(ErrorStatus.EMPTY_FILE);
        }

        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new ProjectException(ErrorStatus.INVALID_CONTENT_TYPE);
        }
    }

    private static void validateUserAuth(Long userId, Project project) {
        if (!Objects.equals(project.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }
    }

    private String saveImage(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(FILE_DIR + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ProjectException(ErrorStatus.FILE_UPLOAD_FAILED);
        }
        return fileName;
    }

    private void deleteImage(String imagePath) {
        File file = new File(FILE_DIR + imagePath);
        if (file.exists()) {
            if (!file.delete()) {
                log.info("[ProjectCommandService][delete] Project image is not deleted: {}", imagePath);
            }
        }
    }

    private void mapToProject(ProjectDetailDto projectDetailDto, Project project) {
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
        List<Technology> technologyList = projectDetailDto.getTechnologyNameList().stream().map(
                Technology::new
        ).toList();

        technologyList.forEach(technology -> technology.setProject(project));
    }

    private void setPositionToProject(ProjectDetailDto projectDetailDto, Project project) {
        List<PositionContext> positionContextList = projectDetailDto.getPositionDtoList().stream().map(
                positionDto -> new PositionContext(
                        positionRepository.findById(positionDto.getPositionId())
                                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND)),
                        positionDto.getMemberCount()
                )
        ).toList();

        List<ProjectRecruitPosition> projectPositionList = ProjectPositionConverter.toProjectPositionList(positionContextList);
        projectPositionList.forEach(projectPosition -> projectPosition.setProject(project));
    }

    private void setUserToProject(Long userId, Project project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        project.setUser(user);
    }

    private ProjectRecruitPosition getProjectPosition(Long userId, Long projectId, ApplyDto applyDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        if (project.getUser().getId().equals(userId)) {
            throw new UserException(ErrorStatus.OWN_PROJECT);
        }

        Position position = positionRepository.findById(applyDto.getPositionId())
                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND));

        return projectPositionRepository.findByProjectAndPosition(project, position)
                .orElseThrow(() -> new ProjectException(ErrorStatus.NOT_RECRUITING_POSITION));
    }

    private static void validateMatchingNotCompleted(ProjectRecruitPosition projectPosition) {
        int matchedCount = 0;
        List<ProjectMatch> projectMatchList = projectPosition.getProjectMatchList();
        for (ProjectMatch projectMatch : projectMatchList) {
            if (projectMatch.getState() == MatchState.MATCH) {
                matchedCount++;
            }
        }

        if (projectPosition.getMemberCount() <= matchedCount) {
            throw new ProjectException(ErrorStatus.ALREADY_MATCHING_END_POSITION);
        }
    }

    private void validateAlreadyApplied(User user, ProjectRecruitPosition projectPosition) {
        boolean exists = projectMatchRepository.existsByUserAndProjectPosition(user, projectPosition);
        if (exists) {
            throw new UserException(ErrorStatus.ALREADY_APPLIED_USER);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PositionContext {
        private Position position;
        private Integer memberCount;
    }
}
