package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.service.UserCommandService;
import com.sphere.demo.service.project.ProjectQueryService;
import com.sphere.demo.web.dto.UserRequestDto.JoinDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sphere.demo.web.dto.UserRequestDto.*;

@RestController
@RequestMapping(("/users"))
@RequiredArgsConstructor
public class UserRestController {

    private final UserCommandService userCommandService;
    private final ProjectQueryService projectQueryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Void> join(@RequestBody @Valid JoinDto joinDto) {
        userCommandService.join(joinDto);
        return ApiResponse.of(SuccessStatus._CREATED, null);
    }

    @PostMapping("/projects/{projectId}")
    public ApiResponse<Void> apply(@AuthenticationPrincipal Long userId,
                                   @PathVariable Long projectId,
                                   @RequestBody @Valid ApplyDto applyDto) {
        ProjectRecruitPosition projectPosition = projectQueryService.findProjectPosition(projectId, applyDto);
        userCommandService.applyProject(userId, projectPosition);
        return ApiResponse.onSuccess(null);
    }
}
