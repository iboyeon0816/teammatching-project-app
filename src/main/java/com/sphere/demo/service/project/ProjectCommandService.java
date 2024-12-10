package com.sphere.demo.service.project;


import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.*;
import com.sphere.demo.domain.*;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.domain.mapping.ProjectTechnology;
import com.sphere.demo.exception.ex.*;
import com.sphere.demo.repository.*;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ApplyDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.UpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandService {

    private final PlatformRepository platformRepository;
    private final TechnologyRepository technologyRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private final ProjectMatchRepository projectMatchRepository;
    private final ProjectRecruitPositionRepository projectPositionRepository;

    public Project create(Long userId, CreateDto createDto) {
        Project project = ProjectConverter.toProject(createDto);
        mapToProject(userId, createDto, project);
        return projectRepository.save(project);
    }

    public void update(Long userId, Long projectId, UpdateDto updateDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        validateUserAuth(userId, project);
        project.update(updateDto);
    }

    public void delete(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        validateUserAuth(userId, project);
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

    private static void validateUserAuth(Long userId, Project project) {
        if (!Objects.equals(project.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }
    }

    private void mapToProject(Long userId, CreateDto createDto, Project project) {
        setPlatformToProject(createDto, project);
        setTechnologyToProject(createDto, project);
        setPositionToProject(createDto, project);
        setUserToProject(userId, project);
    }

    private void setPlatformToProject(CreateDto createDto, Project project) {
        List<Platform> platformList = createDto.getPlatformIdList().stream().map(
                platformId -> platformRepository.findById(platformId)
                        .orElseThrow(() -> new PlatformException(ErrorStatus.PLATFORM_NOT_FOUND))
        ).toList();

        List<ProjectPlatform> projectPlatformList = ProjectPlatformConverter.toProjectPlatform(platformList);
        projectPlatformList.forEach(projectPlatform -> projectPlatform.setProject(project));
    }


    private void setTechnologyToProject(CreateDto createDto, Project project) {
        List<Technology> technologyList = createDto.getTechnologyIdList().stream().map(
                techId -> technologyRepository.findById(techId)
                        .orElseThrow(() -> new TechStackException(ErrorStatus.TECH_NOT_FOUND))
        ).toList();

        List<ProjectTechnology> projectTechnologyList = ProjectTechStackConverter.toProjectTechStack(technologyList);
        projectTechnologyList.forEach(projectTechStack -> projectTechStack.setProject(project));
    }

    private void setPositionToProject(CreateDto createDto, Project project) {
        List<PositionContext> positionContextList = createDto.getPositionDtoList().stream().map(
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
