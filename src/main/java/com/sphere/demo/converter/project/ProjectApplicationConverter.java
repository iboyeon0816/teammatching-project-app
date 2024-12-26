package com.sphere.demo.converter.project;

import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.mapping.ProjectApplication;

public class ProjectApplicationConverter {
    public static ProjectApplication toProjectApplication(ResumeSnapshot resumeSnapshot) {
        return ProjectApplication.builder()
                .resumeSnapshot(resumeSnapshot)
                .build();
    }
}
