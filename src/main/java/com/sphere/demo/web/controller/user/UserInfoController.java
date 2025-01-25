package com.sphere.demo.web.controller.user;

import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.converter.userinform.UserInformConverter;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.user.UserInfoCommandService;
import com.sphere.demo.service.user.UserInfoQueryService;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.ModifyDto;
import com.sphere.demo.web.dto.user.UserInfoResponseDto.InformResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserInfoController {
    private final UserInfoQueryService userInfoQueryService;
    private final UserInfoCommandService userInfoCommandService;

    @GetMapping("/{userId}")
    public ApiResponseDto<InformResultDto> getUserInform(@PathVariable("userId") Long userId){
        User user = userInfoQueryService.findById(userId);
        List<Resume> resumes = userInfoQueryService.getPortfolioProjectByUserId(userId);
        return ApiResponseDto.onSuccess(UserInformConverter.toInformResultDto(user, resumes));
    }

    @PatchMapping
    public ApiResponseDto<Void> modifyUserInform(@AuthenticationPrincipal Long userId,
                                                            @RequestBody @Valid ModifyDto request){
        userInfoCommandService.modifyInformUser(request, userId);
        return ApiResponseDto.onSuccess(null);
    }

    @DeleteMapping("/{userId}/delete")
    public ApiResponseDto<Void> deleteUser(@PathVariable("userId") @AuthenticationPrincipal Long userId){
        userInfoCommandService.deleteUser(userId);
        return ApiResponseDto.onSuccess(null);
    }
}
