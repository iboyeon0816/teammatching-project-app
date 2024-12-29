package com.sphere.demo.service.project;


import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ProjectApplicationConverter;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectPosition;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.ProjectApplicationRepository;
import com.sphere.demo.repository.ProjectRecruitPositionRepository;
import com.sphere.demo.repository.ProjectRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.service.common.FileService;
import com.sphere.demo.service.resume.ResumeSnapshotService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectRecruitPositionRepository projectPositionRepository;
    private final ProjectAssociationHelper associationHelper;
    private final FileService fileService;
    private final ResumeSnapshotService resumeSnapshotService;

    public Project create(Long userId, ProjectDetailDto createDto) {
        User user = fetchUser(userId);
        Project project = ProjectConverter.toProject(createDto, user);
        associationHelper.setAssociations(createDto, project);
        return projectRepository.save(project);
    }

    public void update(Long userId, Long projectId, ProjectDetailDto updateDto) {
        Project project = validateAndFetchProject(userId, projectId);
        project.clearAssociations();
        project.update(updateDto);
        associationHelper.setAssociations(updateDto, project);
    }

    public void delete(Long userId, Long projectId) {
        Project project = validateAndFetchProject(userId, projectId);
        fileService.deleteFile(project.getImagePath());
        projectRepository.delete(project);
    }

    public void uploadImage(Long userId, Long projectId, MultipartFile file) {
        Project project = validateAndFetchProject(userId, projectId);
        String fileName = fileService.saveImage(file);
        project.setImagePath(fileName);
    }

    public void updateImage(Long userId, Long projectId, MultipartFile file) {
        Project project = validateAndFetchProject(userId, projectId);
        String newFileName = fileService.saveImage(file);
        fileService.deleteFile(project.getImagePath());
        project.setImagePath(newFileName);
    }

    public ProjectApplication apply(Long userId, Long projectPositionId, Long resumeId) {
        User user = fetchUser(userId);
        ProjectPosition projectPosition = validateAndFetchProjectPosition(user, projectPositionId);
        ResumeSnapshot resumeSnapshot = resumeSnapshotService.saveSnapshot(user, resumeId);
        ProjectApplication projectApplication = ProjectApplicationConverter.toProjectApplication(resumeSnapshot, projectPosition, user);
        return projectApplicationRepository.save(projectApplication);
    }

    public void projectViewUp(Project project) {
        project.viewUp();
    }

    private User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    private Project validateAndFetchProject(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        if (!Objects.equals(project.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return project;
    }

    private ProjectPosition validateAndFetchProjectPosition(User user, Long projectPositionId) {
        ProjectPosition projectPosition = projectPositionRepository.findById(projectPositionId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_POSITION_NOT_FOUND));

        Project project = projectPosition.getProject();
        if (project.getStatus().equals(ProjectState.COMPLETED)) {
            throw new ProjectException(ErrorStatus.ALREADY_COMPLETED_PROJECT);
        }

        Integer memberCount = projectPosition.getMemberCount();
        Integer approvedCount = projectPosition.getApprovedCount();
        if (Objects.equals(memberCount, approvedCount)) {
            throw new ProjectException(ErrorStatus.ALREADY_MATCHING_END_POSITION);
        }

        User owner = project.getUser();
        if (owner.getId().equals(user.getId())) {
            throw new UserException(ErrorStatus.OWN_PROJECT);
        }

        return projectPosition;
    }
}
