package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.service.user.UserCommandService;
import com.sphere.demo.service.user.UserQueryService;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.UpdateDto;
import com.sphere.demo.web.dto.user.UserInfoResponseDto.UserDetailDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
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

    @PostMapping("/images")
    public ApiResponseDto<Void> uploadImage(@AuthenticationPrincipal Long userId,
                                            @RequestParam("file") MultipartFile file) {
        userCommandService.uploadImage(userId, file);
        return ApiResponseDto.onSuccess(null);
    }

    @DeleteMapping("/images")
    public ApiResponseDto<Void> deleteImage(@AuthenticationPrincipal Long userId) {
        userCommandService.deleteImage(userId);
        return ApiResponseDto.onSuccess(null);
    }
}
