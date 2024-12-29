package com.sphere.demo.web.controller.project;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.domain.Project;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.CreateResultDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project", description = "프로젝트 관련 API")
public class ProjectCommandController {

    private final ProjectCommandService projectCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<CreateResultDto> create(@AuthenticationPrincipal Long userId,
                                                  @RequestBody @Valid ProjectDetailDto createDto) {
        Project project = projectCommandService.create(userId, createDto);
        return ApiResponseDto.of(SuccessStatus._CREATED, new CreateResultDto(project.getId()));
    }

    @PutMapping("/{projectId}")
    public ApiResponseDto<Void> update(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long projectId,
                                       @RequestBody @Valid ProjectDetailDto updateDto) {
        projectCommandService.update(userId, projectId, updateDto);
        return ApiResponseDto.onSuccess(null);
    }

    @DeleteMapping("/{projectId}")
    public ApiResponseDto<Void> delete(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long projectId) {
        projectCommandService.delete(userId, projectId);
        return ApiResponseDto.onSuccess(null);
    }

    @PostMapping("/{projectId}/images")
    public ApiResponseDto<Void> uploadImage(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long projectId,
                                            @RequestParam("file") MultipartFile file) {
        projectCommandService.uploadImage(userId, projectId, file);
        return ApiResponseDto.of(SuccessStatus._OK, null);
    }

    @PutMapping("/{projectId}/images")
    public ApiResponseDto<Void> updateImage(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long projectId,
                                            @RequestParam("file") MultipartFile file) {
        projectCommandService.updateImage(userId, projectId, file);
        return ApiResponseDto.of(SuccessStatus._OK, null);
    }

    @PatchMapping("/{projectId}/close")
    public ApiResponseDto<Void> close(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long projectId) {
        projectCommandService.close(userId, projectId);
        return ApiResponseDto.onSuccess(null);
    }
}
