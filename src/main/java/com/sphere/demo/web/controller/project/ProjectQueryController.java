package com.sphere.demo.web.controller.project;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.argument.PageCheck;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.service.project.ProjectQueryService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectSearchCond;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.GetResultDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectQueryController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    @GetMapping
    public ApiResponseDto<ProjectPageDto> getProjects(@PageCheck Integer page,
                                                      @RequestBody(required = false) ProjectSearchCond projectSearchCond) {
        Page<Project> projectPage = projectQueryService.getProjects(projectSearchCond, page);
        return ApiResponseDto.onSuccess(ProjectConverter.toProjectPageDto(projectPage));
    }

    @GetMapping("/{projectId}")
    public ApiResponseDto<ProjectDetailDto> getProjectDetail(@PathVariable Long projectId) {
        Project project = projectQueryService.getProject(projectId);
        projectCommandService.projectViewUp(project);
        return ApiResponseDto.onSuccess(ProjectConverter.toProjectDetailDto(project));
    }

    @GetMapping("/most-views")
    public ApiResponseDto<List<GetResultDto>> getProjectsWithMostViews() {
        List<GetResultDto> getResultDtoList = projectQueryService.getProjectWithMostViews()
                .stream().map(ProjectConverter::toGetResultDto)
                .toList();

        return ApiResponseDto.onSuccess(getResultDtoList);
    }

    @GetMapping("/new")
    public ApiResponseDto<List<GetResultDto>> getNewProjects() {
        List<GetResultDto> getResultDtoList = projectQueryService.getNewProject()
                .stream().map(ProjectConverter::toGetResultDto)
                .toList();

        return ApiResponseDto.onSuccess(getResultDtoList);
    }
}
