package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.ProjectTechnology;

import java.util.List;

public class ProjectTechnologyConverter {
    public static void toProjectTechnology(List<String> technologyNameList, Project project) {
        technologyNameList.forEach(name -> {
            ProjectTechnology projectTechnology = new ProjectTechnology(name);
            projectTechnology.setProject(project);
        });
    }
}
