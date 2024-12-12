package com.sphere.demo.web.controller.project;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ApplyDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.UpdateDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.CreateResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectCommandService projectCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<CreateResultDto> create(@AuthenticationPrincipal Long userId,
                                                  @RequestBody @Valid CreateDto createDto) {
        Project project = projectCommandService.create(userId, createDto);
        return ApiResponseDto.of(SuccessStatus._CREATED, ProjectConverter.toCreateResultDto(project));
    }

    @PostMapping("/{projectId}/images")
    public ApiResponseDto<Void> uploadImage(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long projectId,
                                            @RequestParam("file") MultipartFile file) {
        projectCommandService.uploadImage(userId, projectId, file);
        return ApiResponseDto.of(SuccessStatus._OK, null);
    }

    @PutMapping("/{projectId}")
    public ApiResponseDto<Void> update(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long projectId,
                                       @RequestBody @Valid UpdateDto updateDto) {
        projectCommandService.update(userId, projectId, updateDto);
        return ApiResponseDto.onSuccess(null);
    }

    @DeleteMapping("/{projectId}")
    public ApiResponseDto<Void> delete(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long projectId) {
        projectCommandService.delete(userId, projectId);
        return ApiResponseDto.onSuccess(null);
    }

    @PostMapping("/{projectId}")
    public ApiResponseDto<Void> apply(@AuthenticationPrincipal Long userId,
                                      @PathVariable Long projectId,
                                      @RequestBody @Valid ApplyDto applyDto) {
        projectCommandService.apply(userId, projectId, applyDto);
        return ApiResponseDto.onSuccess(null);
    }
}
