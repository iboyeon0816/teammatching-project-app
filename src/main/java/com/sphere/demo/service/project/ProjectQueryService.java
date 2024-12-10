package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectSearchCond;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectQueryService {
    Page<Project> getProjects(ProjectSearchCond projectSearchCond, Integer page);

    Project getProject(Long projectId);

    List<Project> getProjectWithMostViews();

    List<Project> getNewProject();

}
