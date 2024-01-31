package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectResponseDto;
import com.sphere.demo.web.dto.ProjectResponseDto.CreateResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectCommandService projectCommandService;

    @PostMapping
    public ApiResponse<CreateResultDto> createProject(@AuthenticationPrincipal Long userId,
                                                      @RequestBody @Valid CreateDto createDto) {
        Project project = projectCommandService.createProject(userId, createDto);
        return ApiResponse.onSuccess(ProjectConverter.toCreateResultDto(project));
    }
}
