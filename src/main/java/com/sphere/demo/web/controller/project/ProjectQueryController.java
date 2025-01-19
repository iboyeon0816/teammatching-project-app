package com.sphere.demo.web.controller.project;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.argument.PageCheck;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.service.project.ProjectQueryService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectSearchCond;
import com.sphere.demo.web.dto.project.ProjectResponseDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.MainProjectDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.GetResultDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectQueryController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    @GetMapping("/main")
    public ApiResponseDto<List<MainProjectDto>> getMainProjects(@AuthenticationPrincipal Long userId) {
        List<MainProjectDto> resultDto = projectQueryService.getMainProjects()
                .stream().map(project -> ProjectConverter.toMainProjectDto(project, userId))
                .toList();
        return ApiResponseDto.onSuccess(resultDto);
    }

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
}
