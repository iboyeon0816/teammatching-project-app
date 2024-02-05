package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.service.project.ProjectQueryService;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectResponseDto.CreateResultDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectDetailDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<CreateResultDto> createProject(@AuthenticationPrincipal Long userId,
                                                      @RequestBody @Valid CreateDto createDto) {
        Project project = projectCommandService.create(userId, createDto);
        return ApiResponse.of(SuccessStatus._CREATED, ProjectConverter.toCreateResultDto(project));
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailDto> showProject(@PathVariable Long projectId) {
        Project project = projectQueryService.findProject(projectId);
        projectCommandService.projectViewUp(project);
        return ApiResponse.onSuccess(ProjectConverter.toProjectDetailDto(project));
    }

    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> delete(@PathVariable Long projectId) {
        projectCommandService.delete(projectId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/most-views")
    public ApiResponse<List<ProjectDto>> showProjectWithMostViews() {
        List<ProjectDto> projectDtoList = projectQueryService.findProjectWithMostViews()
                .stream().map(ProjectConverter::toProjectDto)
                .toList();

        return ApiResponse.onSuccess(projectDtoList);
    }

    @GetMapping("/new")
    public ApiResponse<List<ProjectDto>> showNewProject() {
        List<ProjectDto> projectDtoList = projectQueryService.findNewProject()
                .stream().map(ProjectConverter::toProjectDto)
                .toList();

        return ApiResponse.onSuccess(projectDtoList);
    }
}
