package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.web.dto.ProjectRequestDto;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;

public interface ProjectCommandService {
    Project create(Long userId, CreateDto createDto);

    void projectViewUp(Project project);

    void delete(Long userId, Long projectId);

    void update(Long userId, Long projectId, ProjectRequestDto.UpdateDto createDto);
}
