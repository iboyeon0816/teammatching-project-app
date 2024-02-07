package com.sphere.demo.converter.project;

import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.service.project.ProjectCommandServiceImpl.PositionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectPositionConverter {

    public static List<ProjectRecruitPosition> toProjectPositionList(List<PositionContext> positionList) {
        return positionList.stream()
                .map(positionInfo -> ProjectRecruitPosition.builder()
                        .position(positionInfo.getPosition())
                        .memberCount(positionInfo.getMemberCount())
                        .projectMatchList(new ArrayList<>())
                        .build())
                .collect(Collectors.toList());
    }
}
