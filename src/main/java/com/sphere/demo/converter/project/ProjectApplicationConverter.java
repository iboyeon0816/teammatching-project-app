package com.sphere.demo.converter.project;

import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectPosition;

public class ProjectApplicationConverter {
    public static ProjectApplication toProjectApplication(ResumeSnapshot resumeSnapshot, ProjectPosition projectPosition, User user) {
        ProjectApplication projectApplication =  ProjectApplication.builder()
                .resumeSnapshot(resumeSnapshot)
                .build();
        projectApplication.setProjectPosition(projectPosition);
        projectApplication.setUser(user);
        return projectApplication;
    }
}
