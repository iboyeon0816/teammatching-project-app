package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.converter.userinform.UserInformConverter;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.userinform.UserInformCommandService;
import com.sphere.demo.service.userinform.UserInformQueryService;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserInformResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserInformController {
    private final UserInformQueryService userInformQueryService;
    private final UserInformCommandService userInformCommandService;

    @GetMapping("/{userId}")
    public ApiResponseDto<UserInformResponseDto.InformResultDto> getUserInform(@PathVariable("userId") Long userId){
        User user = userInformQueryService.findById(userId);
        List<Resume> resumes = userInformQueryService.getPortfolioProjectByUserId(userId);
        return ApiResponseDto.onSuccess(UserInformConverter.toInformResultDto(user, resumes));
    }

    @PutMapping("/{userId}/modify")
    public ApiResponseDto<UserInformResponseDto.InformResultDto> modifyUserInform(@PathVariable("userId") @AuthenticationPrincipal Long userId, @RequestBody @Valid UserInformRequestDto.ModifyDto request){
        userInformCommandService.modifyInformUser(request, userId);
        return ApiResponseDto.onSuccess(null);
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponseDto<Void> deleteUser(@PathVariable("userId") @AuthenticationPrincipal Long userId){
        userInformCommandService.deleteUser(userId);
        return ApiResponseDto.onSuccess(null);
    }
}
