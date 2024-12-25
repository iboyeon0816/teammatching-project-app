package com.sphere.demo.converter.project;

import com.sphere.demo.domain.User;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectPosition;

public class ProjectMatchConverter {
    public static ProjectMatch toProjectMatch(User user, ProjectPosition projectPosition) {
        return ProjectMatch.builder()
                .state(MatchState.UNMATCH)
                .user(user)
                .projectPosition(projectPosition)
                .build();
    }
}
