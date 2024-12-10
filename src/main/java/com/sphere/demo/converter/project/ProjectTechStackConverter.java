package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Technology;
import com.sphere.demo.domain.mapping.ProjectTechnology;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTechStackConverter {
    public static List<ProjectTechnology> toProjectTechStack(List<Technology> technologyList) {
        return technologyList.stream()
                .map(techStack -> ProjectTechnology.builder()
                        .technology(techStack)
                        .build())
                .collect(Collectors.toList());
    }
}
