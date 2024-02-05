package com.sphere.demo.service.project;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.converter.project.ProjectPlatformConverter;
import com.sphere.demo.converter.project.ProjectPositionConverter;
import com.sphere.demo.converter.project.ProjectTechStackConverter;
import com.sphere.demo.domain.*;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.domain.mapping.ProjectTechStack;
import com.sphere.demo.exception.ex.PlatformException;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.TechStackException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.*;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final PlatformRepository platformRepository;
    private final TechStackRepository techStackRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(Long userId, CreateDto createDto) {

        Project project = ProjectConverter.toProject(createDto);

        setPlatformToProject(createDto, project);
        setTechStackToProject(createDto, project);
        setPositionToProject(createDto, project);
        setUserToProject(userId, project);

        return projectRepository.save(project);
    }

    @Override
    public void projectViewUp(Project project) {
        project.viewUp();
    }

    private void setPlatformToProject(CreateDto createDto, Project project) {
        List<Platform> platformList = findPlatformEntity(createDto);
        List<ProjectPlatform> projectPlatformList = ProjectPlatformConverter.toProjectPlatform(platformList);
        projectPlatformList.forEach(projectPlatform -> projectPlatform.setProject(project));
    }

    private List<Platform> findPlatformEntity(CreateDto createDto) {
        return createDto.getPlatformIdList().stream().map(
                platformId -> platformRepository.findById(platformId)
                        .orElseThrow(() -> new PlatformException(ErrorStatus.PLATFORM_NOT_FOUND))
        ).toList();
    }

    private void setTechStackToProject(CreateDto createDto, Project project) {
        List<TechnologyStack> techStackList = findTechStackEntity(createDto);

        List<ProjectTechStack> projectTechStackList = ProjectTechStackConverter.toProjectTechStack(techStackList);
        projectTechStackList.forEach(projectTechStack -> projectTechStack.setProject(project));
    }

    private List<TechnologyStack> findTechStackEntity(CreateDto createDto) {
        return createDto.getTechStackIdList().stream().map(
                techStackId -> techStackRepository.findById(techStackId)
                        .orElseThrow(() -> new TechStackException(ErrorStatus.TECH_STACK_NOT_FOUND))
        ).toList();
    }

    private void setPositionToProject(CreateDto createDto, Project project) {
        List<PositionEntityInfo> positionList = findPositionEntity(createDto);
        List<ProjectRecruitPosition> projectPositionList = ProjectPositionConverter.toProjectPositionList(positionList);
        projectPositionList.forEach(projectPosition -> projectPosition.setProject(project));
    }

    private List<PositionEntityInfo> findPositionEntity(CreateDto createDto) {
        return createDto.getPositionInfoList().stream().map(
                positionInfo -> new PositionEntityInfo(
                        positionRepository.findById(positionInfo.getPositionId())
                                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND)),
                        positionInfo.getMemberCount()
                )
        ).toList();
    }


    private void setUserToProject(Long userId, Project project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        project.setUser(user);
    }

    @Getter
    @AllArgsConstructor
    public static class PositionEntityInfo {
        private Position position;
        private Integer memberCount;
    }
}
