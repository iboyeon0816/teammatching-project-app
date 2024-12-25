package com.sphere.demo.web.controller.resume;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.service.resume.ResumeService;
import com.sphere.demo.web.dto.user.ResumeRequestDto.ResumeDetailDto;
import com.sphere.demo.web.dto.user.ResumeResponseDto.AddResultDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
@Tag(name = "Resume", description = "사용자 이력서 관련 API")
public class ResumeController {

    private final ResumeService resumeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<AddResultDto> addResume(@AuthenticationPrincipal Long userId,
                                                  @RequestBody @Valid ResumeDetailDto resumeDetailDto){
        Resume resume = resumeService.addResume(userId, resumeDetailDto);
        return ApiResponseDto.onSuccess(new AddResultDto(resume.getId()));
    }

    @PutMapping("/{resumeId}")
    public ApiResponseDto<Void> update(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long resumeId,
                                       @RequestBody @Valid ResumeDetailDto resumeDetailDto) {
        resumeService.update(userId, resumeId, resumeDetailDto);
        return ApiResponseDto.onSuccess(null);
    }
}
