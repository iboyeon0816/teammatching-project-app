package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.service.user.RefreshTokenService;
import com.sphere.demo.service.user.UserCommandService;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.JoinDto;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.RefreshDto;
import com.sphere.demo.web.dto.user.UserAuthResponseDto.RefreshSuccessDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAuthController {

    private final UserCommandService userCommandService;
    private final RefreshTokenService refreshTokenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ApiResponseDto<Void> join(@RequestBody @Valid JoinDto joinDto) {
        userCommandService.join(joinDto);
        return ApiResponseDto.of(SuccessStatus._CREATED, null);
    }

    @PostMapping("/refresh")
    public ApiResponseDto<RefreshSuccessDto> refresh(@RequestBody @Valid RefreshDto refreshDto) {
        RefreshSuccessDto resultDto = refreshTokenService.refresh(refreshDto.getRefreshToken());
        return ApiResponseDto.onSuccess(resultDto);
    }
}
