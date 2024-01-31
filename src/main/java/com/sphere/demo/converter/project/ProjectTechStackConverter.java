package com.sphere.demo.converter.project;

import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.mapping.ProjectTechStack;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTechStackConverter {
    public static List<ProjectTechStack> toProjectTechStack(List<TechnologyStack> technologyStackList) {
        return technologyStackList.stream()
                .map(techStack -> ProjectTechStack.builder()
                        .technologyStack(techStack)
                        .build())
                .collect(Collectors.toList());
    }
}
