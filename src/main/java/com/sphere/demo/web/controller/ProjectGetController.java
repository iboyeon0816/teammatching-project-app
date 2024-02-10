package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.argument.PageCheck;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.service.project.ProjectQueryService;
import com.sphere.demo.web.dto.ProjectRequestDto.ProjectSearchCond;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectDetailDto;
import com.sphere.demo.web.dto.ProjectResponseDto.GetResultDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectGetController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    @GetMapping
    public ApiResponse<ProjectPageDto> getProjects(@PageCheck Integer page,
                                                   @RequestBody(required = false) ProjectSearchCond projectSearchCond) {
        Page<Project> projectPage = projectQueryService.getProjects(projectSearchCond, page);
        return ApiResponse.onSuccess(ProjectConverter.toProjectPageDto(projectPage));
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailDto> getProjectDetail(@PathVariable Long projectId) {
        Project project = projectQueryService.getProject(projectId);
        projectCommandService.projectViewUp(project);
        return ApiResponse.onSuccess(ProjectConverter.toProjectDetailDto(project));
    }

    @GetMapping("/most-views")
    public ApiResponse<List<GetResultDto>> getProjectsWithMostViews() {
        List<GetResultDto> getResultDtoList = projectQueryService.getProjectWithMostViews()
                .stream().map(ProjectConverter::toGetResultDto)
                .toList();

        return ApiResponse.onSuccess(getResultDtoList);
    }

    @GetMapping("/new")
    public ApiResponse<List<GetResultDto>> getNewProjects() {
        List<GetResultDto> getResultDtoList = projectQueryService.getNewProject()
                .stream().map(ProjectConverter::toGetResultDto)
                .toList();

        return ApiResponse.onSuccess(getResultDtoList);
    }
}
