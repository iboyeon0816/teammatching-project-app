package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.service.user.UnivCertService;
import com.sphere.demo.web.dto.user.UnivCertRequestDto.ConfirmDto;
import com.sphere.demo.web.dto.user.UnivCertRequestDto.VerifyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/student")
@RequiredArgsConstructor
public class UnivCertController {

    private final UnivCertService univCertService;

    @PostMapping("/verify")
    public ApiResponseDto<Void> verify(@RequestBody @Valid VerifyDto verifyDto) {
        univCertService.verify(verifyDto.getUnivEmail(), verifyDto.getUnivName());
        return ApiResponseDto.onSuccess(null);
    }

    @PostMapping("/confirm")
    public ApiResponseDto<Void> confirm(@RequestBody @Valid ConfirmDto confirmDto) {
        univCertService.confirm(confirmDto.getUnivEmail(), confirmDto.getUnivName(), confirmDto.getCode());
        return ApiResponseDto.onSuccess(null);
    }
}
