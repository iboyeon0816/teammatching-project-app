package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.service.UnivCertService;
import com.sphere.demo.web.dto.univcert.UnivCertRequestDto.ConfirmDto;
import com.sphere.demo.web.dto.univcert.UnivCertRequestDto.VerifyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재학생 인증 요청에 성공하였습니다."),
            @ApiResponse(responseCode = "409", description = "이미 회원가입된 정보가 존재합니다. 로그인하여 이용해 주세요."),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 대학명입니다."),
            @ApiResponse(responseCode = "404", description = "재학 정보를 찾을 수 없습니다.")
    })
    public ApiResponseDto<Void> verify(@RequestBody @Valid VerifyDto verifyDto) {
        univCertService.verify(verifyDto.getEmail(), verifyDto.getUnivName());
        return ApiResponseDto.onSuccess(null);
    }

    @PostMapping("/confirm")
    @Operation(summary = "인증 코드 검증", description = "사용자 메일에 전송된 인증 코드를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 검증에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 코드입니다.")
    })
    public ApiResponseDto<Void> confirm(@RequestBody @Valid ConfirmDto confirmDto) {
        univCertService.confirm(confirmDto.getEmail(), confirmDto.getUnivName(), confirmDto.getCode());
        return ApiResponseDto.onSuccess(null);
    }
}
