package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.ProjectFavorite;

public class ProjectFavoriteConverter {
    public static ProjectFavorite toProjectFavorite(Project project, User user) {
        ProjectFavorite projectFavorite = new ProjectFavorite(project);
        projectFavorite.setUser(user);
        return projectFavorite;
    }
}
