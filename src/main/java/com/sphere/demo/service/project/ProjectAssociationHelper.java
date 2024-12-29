package com.sphere.demo.service.project;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ProjectPlatformConverter;
import com.sphere.demo.converter.project.ProjectPositionConverter;
import com.sphere.demo.converter.project.ProjectTechnologyConverter;
import com.sphere.demo.domain.Platform;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import com.sphere.demo.exception.ex.PlatformException;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.repository.PlatformRepository;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectAssociationHelper {

    private final PlatformRepository platformRepository;
    private final PositionRepository positionRepository;
    public void setAssociations(ProjectDetailDto projectDetailDto, Project project) {
        setupPlatforms(projectDetailDto, project);
        setupTechnologies(projectDetailDto, project);
        setupPositions(projectDetailDto, project);
    }

    private void setupPlatforms(ProjectDetailDto projectDetailDto, Project project) {
        List<Platform> platformList = platformRepository.findAllById(projectDetailDto.getPlatformIdList());
        if (platformList.size() != projectDetailDto.getPlatformIdList().size()) {
            throw new PlatformException(ErrorStatus.PLATFORM_NOT_FOUND);
        }
        ProjectPlatformConverter.toProjectPlatform(platformList, project);
    }

    private void setupTechnologies(ProjectDetailDto projectDetailDto, Project project) {
        List<String> technologyNameList = projectDetailDto.getTechnologyNameList();
        ProjectTechnologyConverter.toProjectTechnology(technologyNameList, project);
    }

    private void setupPositions(ProjectDetailDto projectDetailDto, Project project) {
        List<PositionContext> positionContextList = projectDetailDto.getPositionDtoList().stream().map(
                positionDto -> new PositionContext(
                        positionRepository.findById(positionDto.getPositionId())
                                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND)),
                        positionDto.getMemberCount()
                )
        ).toList();
        ProjectPositionConverter.toProjectPositionList(positionContextList, project);
    }

    @Getter
    @AllArgsConstructor
    public static class PositionContext {
        private Position position;
        private Integer memberCount;
    }
}
