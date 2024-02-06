package com.sphere.demo.converter;

import com.sphere.demo.domain.Comment;
import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.Project;
import com.sphere.demo.web.dto.CommentRequestDto;
import com.sphere.demo.web.dto.CommunityRequestDto;
import com.sphere.demo.web.dto.CommunityResponseDto;
import com.sphere.demo.web.dto.ProjectResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommunityConverter {
    public static List<Community> toCommunityShowDto;

    public static Community toCommunity(CommunityRequestDto.CreateDto createDto) {
        return Community.builder()
                .title(createDto.getTitle())
                .body(createDto.getBody())
                .build();
    }

    public static Comment toComment(CommentRequestDto.CreateCommentDto createCommentDto) {
        return Comment.builder()
                .text(createCommentDto.getText())
                .build();
    }

    public static CommunityResponseDto.CommunityShowDto toCommunityShowDto(Community community) {
        return CommunityResponseDto.CommunityShowDto.builder()
                .title(community.getTitle())
                .body(community.getBody())
                .createdAt(LocalDate.from(community.getCreatedAt()))
                .nickname(community.getUser().getNickname())
                .build();
    }

    public static List<CommunityResponseDto.CommunityShowDto> toCommunityShowDtoList(List<Community> communityList) {
        List<CommunityResponseDto.CommunityShowDto> showDtoList = new ArrayList<>();
        for (Community community : communityList) {
            showDtoList.add(toCommunityShowDto(community));
        }
        return showDtoList;
    }

}
