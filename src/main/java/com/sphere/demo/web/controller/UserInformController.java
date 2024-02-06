package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.converter.UserInformConverter;
import com.sphere.demo.converter.UserInformModifyConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.service.UserInformModifyServiceImpl;
import com.sphere.demo.service.UserInformServiceImpl;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserRequestDto;
import com.sphere.demo.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserInformController {
    private final UserInformServiceImpl userService;
    private final UserInformModifyServiceImpl userInformModifyService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto.InformResultDto> getUserInform(@PathVariable("userId") Long userId){
        User user = userService.findById(userId);
        List<UserPosition> positions = userService.getPositionsByUserId(userId);
        List<UserTechStack> techStacks = userService.getTechStacksByUserId(userId);
        return ApiResponse.onSuccess(UserInformConverter.toInformResultDto(user,positions,techStacks));
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponseDto.InformResultDto> putUserInform(@PathVariable("userId") Long userId, @RequestBody @Valid UserInformRequestDto.ModifyDto request){
        User user = userInformModifyService.ModifyUser(request, userId);
        List<UserPosition> positions = userInformModifyService.getPositionsByUserId(userId);
        List<UserTechStack> techStacks = userInformModifyService.getTechStacksByUserId(userId);
        return ApiResponse.onSuccess(UserInformConverter.toInformResultDto(user,positions,techStacks));
    }
}
