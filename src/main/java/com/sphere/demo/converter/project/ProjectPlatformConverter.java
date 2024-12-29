package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Platform;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectPlatform;

import java.util.List;

public class ProjectPlatformConverter {
    public static void toProjectPlatform(List<Platform> platformList, Project project) {
         platformList.forEach(platform -> {
             ProjectPlatform projectPlatform = new ProjectPlatform(platform);
             projectPlatform.setProject(project);
         });
    }
}
