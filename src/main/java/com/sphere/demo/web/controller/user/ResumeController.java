package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.service.user.ResumeService;
import com.sphere.demo.web.dto.user.ResumeRequestDto.AddResumeDto;
import com.sphere.demo.web.dto.user.ResumeResponseDto.AddResultDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
@Tag(name = "Resume", description = "사용자 이력서 관련 API")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ApiResponseDto<AddResultDto> addResume(@AuthenticationPrincipal Long userId,
                                                  @RequestBody @Valid AddResumeDto addResumeDto){
        Resume resume = resumeService.addResume(userId, addResumeDto);
        return ApiResponseDto.onSuccess(new AddResultDto(resume.getId()));
    }
}
