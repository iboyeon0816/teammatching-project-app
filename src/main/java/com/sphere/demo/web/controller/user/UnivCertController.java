package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.service.user.UnivCertService;
import com.sphere.demo.web.dto.user.UnivCertRequestDto.ConfirmDto;
import com.sphere.demo.web.dto.user.UnivCertRequestDto.VerifyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/student")
@RequiredArgsConstructor
@Tag(name = "University Authentication", description = "대학 인증 관련 API")
public class UnivCertController {

    private final UnivCertService univCertService;

    @PostMapping("/verify")
    @Operation(summary = "대학 인증 요청", description = "대학 이메일과 학교명을 입력받아 재학생 인증을 요청합니다.")
    public ApiResponseDto<Void> verify(@RequestBody @Valid VerifyDto verifyDto) {
        univCertService.verify(verifyDto.getEmail(), verifyDto.getUnivName());
        return ApiResponseDto.onSuccess(null);
    }

    @PostMapping("/confirm")
    @Operation(summary = "인증 코드 검증", description = "사용자 메일에 전송된 인증 코드를 검증합니다.")
    public ApiResponseDto<Void> confirm(@RequestBody @Valid ConfirmDto confirmDto) {
        univCertService.confirm(confirmDto.getEmail(), confirmDto.getUnivName(), confirmDto.getCode());
        return ApiResponseDto.onSuccess(null);
    }
}
