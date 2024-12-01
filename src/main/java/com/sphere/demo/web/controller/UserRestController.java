package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.service.UserCommandService;
import com.sphere.demo.web.dto.UserRequestDto.JoinDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserCommandService userCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<Void> join(@RequestBody @Valid JoinDto joinDto) {
        userCommandService.join(joinDto);
        return ApiResponseDto.of(SuccessStatus._CREATED, null);
    }
}
