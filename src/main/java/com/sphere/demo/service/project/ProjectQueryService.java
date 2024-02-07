package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.ProjectRequestDto.ProjectSearchCond;
import com.sphere.demo.web.dto.UserRequestDto.ApplyDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectQueryService {
    Page<Project> getProjects(ProjectSearchCond projectSearchCond, Integer page);

    Project getProject(Long projectId);

    List<Project> getProjectWithMostViews();

    List<Project> getNewProject();

    ProjectRecruitPosition findProjectPosition(Long projectId, ApplyDto applyDto);
}
