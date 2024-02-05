package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.converter.CommunityConverter;
import com.sphere.demo.domain.Community;
import com.sphere.demo.repository.CommunityRepository;
import com.sphere.demo.service.Community.CommunityQueryService;
import com.sphere.demo.service.Community.CommunityCommandService;
import com.sphere.demo.service.Community.CommunityQueryServiceImpl;
import com.sphere.demo.web.dto.CommunityRequestDto;
import com.sphere.demo.web.dto.CommunityResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityRestContoller {
    private final CommunityCommandService communityCommandService;
    private final CommunityQueryService communityQueryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/community")
    public ApiResponse<Void> create(@AuthenticationPrincipal Long userId,
            @RequestBody @Valid CommunityRequestDto.CreateDto createDto) {
        communityCommandService.create(userId, createDto);
        return ApiResponse.of(SuccessStatus._CREATED, null);
    }

    @GetMapping("/community")
    public ApiResponse<List<CommunityResponseDto.CommunityShowDto>> showCommunity() {
        List<Community> communityList = communityQueryService.getAllCommunity();
        List<CommunityResponseDto.CommunityShowDto> showDtoList = CommunityConverter.toCommunityShowDtoList(communityList);
        return ApiResponse.onSuccess(showDtoList);
    }



}
