package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.UserRequestDto.ApplyDto;

import java.util.List;

public interface ProjectQueryService {
    Project findProject(Long projectId);

    ProjectRecruitPosition findProjectPosition(Long projectId, ApplyDto applyDto);

    List<Project> findProjectWithMostViews();

    List<Project> findNewProject();
}
