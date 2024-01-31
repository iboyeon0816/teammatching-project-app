package com.sphere.demo.converter.project;

import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.service.project.ProjectCommandServiceImpl.PositionEntityInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectPositionConverter {

    public static List<ProjectRecruitPosition> toProjectPositionList(List<PositionEntityInfo> positionList) {
        return positionList.stream()
                .map(positionInfo -> ProjectRecruitPosition.builder()
                        .position(positionInfo.getPosition())
                        .memberCount(positionInfo.getMemberCount())
                        .build())
                .collect(Collectors.toList());
    }
}
