package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.service.UserCommandService;
import com.sphere.demo.web.dto.UserRequestDto.JoinDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserCommandService userCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ApiResponse<Void> join(@RequestBody @Valid JoinDto joinDto) {
        userCommandService.join(joinDto);
        return ApiResponse.of(SuccessStatus._CREATED, null);
    }
}
