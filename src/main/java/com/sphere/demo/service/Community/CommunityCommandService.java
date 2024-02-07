package com.sphere.demo.service.Community;

import com.sphere.demo.domain.Community;
import com.sphere.demo.repository.CommunityRepository;
import com.sphere.demo.web.dto.CommentRequestDto;
import com.sphere.demo.web.dto.CommunityRequestDto.CreateDto;

public interface CommunityCommandService {
    void create(Long userId, CreateDto createDto);

    void communityViewUp(Community community);

    void createComment(Long userId, Long communityId, CommentRequestDto.CreateCommentDto createCommentDto);

}
