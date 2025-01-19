package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectPosition;
import com.sphere.demo.service.project.ProjectAssociationHelper.PositionContext;
import com.sphere.demo.web.dto.project.ProjectResponseDto.PositionDetailDto;

import java.util.List;

public class ProjectPositionConverter {

    public static void toProjectPositionList(List<PositionContext> positionList, Project project) {
         positionList.forEach(positionInfo -> {
             ProjectPosition projectPosition = ProjectPosition.builder()
                     .position(positionInfo.getPosition())
                     .memberCount(positionInfo.getMemberCount())
                     .build();
             projectPosition.setProject(project);
         });
    }

    public static PositionDetailDto toPositionDetailDto(ProjectPosition projectPosition) {
        return PositionDetailDto.builder()
                .positionName(projectPosition.getPosition().getName())
                .totalNumber(projectPosition.getMemberCount())
                .matchedNumber(projectPosition.getMatchedCount())
                .build();
    }
}