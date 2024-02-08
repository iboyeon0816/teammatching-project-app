package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.converter.UserInformConverter;
import com.sphere.demo.converter.UserInformModifyConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.service.*;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserRequestDto;
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
//    private final UserInformModifyService userInformModifyService;
    private final UserInformDeleteService userInformDeleteService;
    private final UserInformUpdateService userInformUpdateService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto.InformResultDto> getUserInform(@PathVariable("userId") Long userId){
        User user = userInformService.findById(userId);
        List<UserPosition> positions = userInformService.getPositionsByUserId(userId);
        List<UserTechStack> techStacks = userInformService.getTechStacksByUserId(userId);
        return ApiResponse.onSuccess(UserInformConverter.toInformResultDto(user,positions,techStacks));
    }

//    @PutMapping("/{userId}")
//    public ApiResponse<UserResponseDto.InformResultDto> putUserInform(@PathVariable("userId") @AuthenticationPrincipal Long userId, @RequestBody @Valid UserInformRequestDto.ModifyDto request){
//        User user = userInformModifyService.modifyUser(request, userId);
//        List<UserPosition> positions = userInformModifyService.getPositionsByUserId(userId);
//        List<UserTechStack> techStacks = userInformModifyService.getTechStacksByUserId(userId);
//        return ApiResponse.onSuccess(UserInformConverter.toResultModifyDto(user, positions, techStacks));
//    }

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
}
