package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;

import com.sphere.demo.web.dto.ProjectRequestDto.ApplyDto;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectRequestDto.UpdateDto;

public interface ProjectCommandService {
    Project create(Long userId, CreateDto createDto);

    void update(Long userId, Long projectId, UpdateDto createDto);

    void delete(Long userId, Long projectId);

    void apply(Long userId, Long projectId, ApplyDto applyDto);

    void projectViewUp(Project project);
}
