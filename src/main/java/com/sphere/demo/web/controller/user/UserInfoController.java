package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.service.user.UserCommandService;
import com.sphere.demo.service.user.UserQueryService;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.UpdateDto;
import com.sphere.demo.web.dto.user.UserInfoResponseDto.UserDetailDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Information", description = "사용자 정보 관련 API")
public class UserInfoController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping
    public ApiResponseDto<UserDetailDto> getUserDetail(@AuthenticationPrincipal Long userId){
        UserDetailDto resultDto = userQueryService.getUserDetail(userId);
        return ApiResponseDto.onSuccess(resultDto);
    }

    @PatchMapping
    public ApiResponseDto<Void> updateUser(@AuthenticationPrincipal Long userId,
                                           @RequestBody @Valid UpdateDto updateDto){
        userCommandService.updateUser(userId, updateDto);
        return ApiResponseDto.onSuccess(null);
    }
}
