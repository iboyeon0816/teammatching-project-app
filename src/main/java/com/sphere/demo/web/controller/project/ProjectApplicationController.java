package com.sphere.demo.web.controller.project;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.service.project.ProjectCommandService;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ApplyDto;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ApproveDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ApplyResultDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Tag(name = "Project Application", description = "프로젝트 지원 관련 API")
public class ProjectApplicationController {

    private final ProjectCommandService projectCommandService;

    @PostMapping("/{projectPositionId}")
    public ApiResponseDto<ApplyResultDto> apply(@AuthenticationPrincipal Long userId,
                                                @PathVariable Long projectPositionId,
                                                @RequestBody @Valid ApplyDto applyDto) {
        ProjectApplication projectApplication = projectCommandService.apply(userId, projectPositionId, applyDto.getResumeId());
        return ApiResponseDto.onSuccess(new ApplyResultDto(projectApplication.getId()));
    }

    @PatchMapping("/{applicationId}")
    public ApiResponseDto<Void> approve(@AuthenticationPrincipal Long userId,
                                                @PathVariable Long applicationId,
                                                @RequestBody @Valid ApproveDto approveDto) {
        projectCommandService.approve(userId, applicationId, approveDto.getApplicationStateRequest());
        return ApiResponseDto.onSuccess(null);
    }
}
