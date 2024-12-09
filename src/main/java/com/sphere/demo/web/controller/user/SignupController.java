package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.converter.UserConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.user.UserCommandService;
import com.sphere.demo.web.dto.user.UserRequestDto.JoinDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sign Up", description = "회원가입 관련 API")
public class SignupController {

    private final UserCommandService userCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    @Operation(summary = "회원가입 요청", description = "대학 이메일, 비밀번호, 닉네임을 받아 회원가입을 요청합니다.")
    public ApiResponseDto<Void> join(@RequestBody @Valid JoinDto joinDto) {
        User user = UserConverter.toUser(joinDto);
        userCommandService.join(user, joinDto.getPassword());
        return ApiResponseDto.of(SuccessStatus._CREATED, null);
    }
}
