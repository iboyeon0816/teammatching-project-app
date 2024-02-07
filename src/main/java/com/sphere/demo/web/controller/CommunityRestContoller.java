package com.sphere.demo.web.controller;

import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.apipayload.status.SuccessStatus;
import com.sphere.demo.converter.CommunityConverter;
import com.sphere.demo.converter.project.ProjectConverter;
import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.Project;
import com.sphere.demo.repository.CommentRepository;
import com.sphere.demo.service.Community.CommunityQueryService;
import com.sphere.demo.service.Community.CommunityCommandService;
import com.sphere.demo.service.Community.CommunityQueryServiceImpl;
import com.sphere.demo.web.dto.CommunityRequestDto;
import com.sphere.demo.web.dto.CommentRequestDto;
import com.sphere.demo.web.dto.CommunityResponseDto;
import com.sphere.demo.web.dto.ProjectResponseDto;
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

    @DeleteMapping("/community/{communityId}")
    public ApiResponse<Void> delete(@AuthenticationPrincipal Long userId, @PathVariable Long communityId) {
        boolean isDeleted = communityQueryService.deleteCommunity(userId, communityId);

        if (isDeleted) {
            return ApiResponse.of(SuccessStatus._DELETED, null);
        } else {
            return ApiResponse.onFailure(ErrorStatus.COMMUNITY_NOT_FOUND, null);
        }
    }

    @PostMapping("/community/{communityId}")
    public ApiResponse<Void> createComment(@AuthenticationPrincipal Long userId,
                                    @PathVariable Long communityId,
                                    @RequestBody @Valid CommentRequestDto.CreateCommentDto createCommentDto) {
        communityCommandService.createComment(userId, communityId, createCommentDto);
        return ApiResponse.of(SuccessStatus._CREATED, null);
    }

    @PutMapping("/community/{communityId}")
    public ApiResponse<Void> updateCommunity(@AuthenticationPrincipal Long userId,
                                             @PathVariable Long communityId,
                                             @RequestBody @Valid CommunityRequestDto.UpdateDto updateDto) {
        communityQueryService.updateCommunity(userId, communityId, updateDto);
        return ApiResponse.of(SuccessStatus._UPDATED, null);
    }



    @GetMapping("/community/most-views")
    public ApiResponse<List<CommunityResponseDto.CommunityShowDto>> mostViewCommunity() {
        List<Community> communityList = communityQueryService.getAllCommunity();
        List<CommunityResponseDto.CommunityShowDto> showMostViewList = CommunityConverter.toCommunityShowMostViewList(communityList);
        return ApiResponse.onSuccess(showMostViewList);
    }

    @GetMapping("community/{communityId}")
    public ApiResponse<CommunityResponseDto.CommunityShowDto> getCommunity(@PathVariable Long communityId) {
        Community community = communityQueryService.findCommunity(communityId);
        communityCommandService.communityViewUp(community);
        return ApiResponse.onSuccess(CommunityConverter.toCommunityInfoDto(community));
    }




}
