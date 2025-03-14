package com.sphere.demo.service.project;


import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ProjectApplicationConverter;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.converter.project.ProjectFavoriteConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.enums.ApplicationState;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectFavorite;
import com.sphere.demo.domain.mapping.ProjectPosition;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.*;
import com.sphere.demo.service.common.FileService;
import com.sphere.demo.service.common.enums.ImageType;
import com.sphere.demo.service.resume.ResumeSnapshotService;
import com.sphere.demo.web.dto.enums.ApplicationStateRequest;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectRecruitPositionRepository projectPositionRepository;
    private final ProjectFavoriteRepository projectFavoriteRepository;
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
        Project project = fetchMyProject(userId, projectId);
        project.clearAssociations();
        project.update(updateDto);
        associationHelper.setAssociations(updateDto, project);
    }

    public void delete(Long userId, Long projectId) {
        Project project = fetchMyProject(userId, projectId);
        fileService.deleteFile(project.getImagePath(), ImageType.PROJECT);
        projectRepository.delete(project);
    }

    public void uploadImage(Long userId, Long projectId, MultipartFile file) {
        Project project = fetchMyProject(userId, projectId);
        String fileName = fileService.saveImage(file, ImageType.PROJECT);
        project.setImagePath(fileName);
    }

    public void updateImage(Long userId, Long projectId, MultipartFile file) {
        Project project = fetchMyProject(userId, projectId);
        String newFileName = fileService.saveImage(file, ImageType.PROJECT);
        fileService.deleteFile(project.getImagePath(), ImageType.PROJECT);
        project.setImagePath(newFileName);
    }

    public ProjectApplication apply(Long userId, Long projectPositionId, Long resumeId) {
        User user = fetchUser(userId);
        ProjectPosition projectPosition = fetchProjectPosition(user, projectPositionId);
        ResumeSnapshot resumeSnapshot = resumeSnapshotService.saveSnapshot(user, resumeId);
        ProjectApplication projectApplication = ProjectApplicationConverter.toProjectApplication(resumeSnapshot, projectPosition, user);
        return projectApplicationRepository.save(projectApplication);
    }

    public void approve(Long userId, Long applicationId, ApplicationStateRequest state) {
        ProjectApplication projectApplication = fetchProjectApplication(applicationId, userId);
        projectApplication.setState(ApplicationState.valueOf(state.name()));
    }

    public void close(Long userId, Long projectId) {
        Project project = fetchMyProject(userId, projectId);
        project.setClose();
    }

    public boolean toggleFavorite(Long userId, Long projectId) {
        User user = fetchUser(userId);
        Project project = fetchProject(projectId);

        Optional<ProjectFavorite> projectFavorite = projectFavoriteRepository.findByUserAndProject(user, project);
        if (projectFavorite.isPresent()) {
            projectFavoriteRepository.delete(projectFavorite.get());
            return false;
        }
        else {
            ProjectFavorite newProjectFavorite = ProjectFavoriteConverter.toProjectFavorite(project, user);
            projectFavoriteRepository.save(newProjectFavorite);
            return true;
        }
    }

    public void closeExpiredProjects() {
        List<Project> expiredProjects = projectRepository.findExpiredProjects(LocalDate.now());

        for (Project project : expiredProjects) {
            project.setClose();
        }
    }

    public void projectViewUp(Project project) {
        project.viewUp();
    }

    private User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    private Project fetchMyProject(Long userId, Long projectId) {
        Project project = fetchProject(projectId);

        if (!Objects.equals(project.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return project;
    }

    private Project fetchProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
    }

    private ProjectPosition fetchProjectPosition(User user, Long projectPositionId) {
        ProjectPosition projectPosition = projectPositionRepository.findById(projectPositionId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_POSITION_NOT_FOUND));

        Project project = validateNotMatchingEnd(projectPosition);

        User owner = project.getUser();
        if (owner.getId().equals(user.getId())) {
            throw new UserException(ErrorStatus.OWN_PROJECT);
        }

        return projectPosition;
    }

    private ProjectApplication fetchProjectApplication(Long applicationId, Long userId) {
        User user = fetchUser(userId);

        ProjectApplication projectApplication = projectApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.APPLICATION_NOT_FOUND));

        Project project = validateNotMatchingEnd(projectApplication.getProjectPosition());

        User owner = project.getUser();
        if (!owner.getId().equals(user.getId())) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        if (!ApplicationState.PENDING.equals(projectApplication.getState())) {
            throw new ProjectException(ErrorStatus.APPLICATION_STATE_FINALIZED);
        }

        return projectApplication;
    }

    private static Project validateNotMatchingEnd(ProjectPosition projectPosition) {
        Project project = projectPosition.getProject();
        if (project.getStatus().equals(ProjectState.COMPLETED)) {
            throw new ProjectException(ErrorStatus.ALREADY_COMPLETED_PROJECT);
        }

        boolean positionFull = projectPosition.isPositionFull();
        if (positionFull) {
            throw new ProjectException(ErrorStatus.ALREADY_MATCHING_END_POSITION);
        }
        return project;
    }
}
