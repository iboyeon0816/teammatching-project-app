package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.converter.UserInformConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.service.userinform.*;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserInformController {
    private final UserInformService userInformService;
    private final UserInformModifyService userInformModifyService;
    private final UserInformDeleteService userInformDeleteService;
    private final UserInformUpdateService userInformUpdateService;
    private final UserDeleteService userDeleteService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto.InformResultDto> getUserInform(@PathVariable("userId") Long userId){
        User user = userInformService.findById(userId);
        List<UserPosition> positions = userInformService.getPositionsByUserId(userId);
        List<UserTechStack> techStacks = userInformService.getTechStacksByUserId(userId);
        return ApiResponse.onSuccess(UserInformConverter.toInformResultDto(user,positions,techStacks));
    }

    @PutMapping("/{userId}/modify")
    public ApiResponse<UserResponseDto.InformResultDto> modifyUserInform(@PathVariable("userId") @AuthenticationPrincipal Long userId, @RequestBody @Valid UserInformRequestDto.ModifyDto request){
        userInformModifyService.modifyUser(request, userId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping ("/{userId}")
    public ApiResponse<Void> deleteUserInform(@PathVariable("userId") @AuthenticationPrincipal Long userId){
        userInformDeleteService.deleteUser(userId);
        return ApiResponse.onSuccess(null);
    }

    @PutMapping ("/{userId}")
    public ApiResponse<Void> updateUserInform(@PathVariable("userId") @AuthenticationPrincipal Long userId, @RequestBody @Valid UserInformRequestDto.ModifyDto request){
        userInformUpdateService.updateUser(request, userId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponse<Void> deleteUser(@PathVariable("userId") @AuthenticationPrincipal Long userId){
        userDeleteService.deleteUser(userId);
        return ApiResponse.onSuccess(null);
    }
}
