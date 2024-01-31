package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Platform;
import com.sphere.demo.domain.mapping.ProjectPlatform;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectPlatformConverter {
    public static List<ProjectPlatform> toProjectPlatform(List<Platform> platformList) {
        return platformList.stream()
                .map(platform -> ProjectPlatform.builder()
                        .platform(platform)
                        .build())
                .collect(Collectors.toList());
    }
}
