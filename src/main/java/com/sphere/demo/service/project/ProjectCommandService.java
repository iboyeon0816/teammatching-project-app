package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;

public interface ProjectCommandService {
    Project createProject(Long userId, CreateDto createDto);

    void projectViewUp(Project project);
}
