package com.sphere.demo.service.Community;

import com.sphere.demo.domain.Community;
import com.sphere.demo.web.dto.CommunityRequestDto;

import java.util.List;

public interface CommunityQueryService {
    List<Community> findAllCommunity();

    List<Community> getAllCommunity();

    boolean deleteCommunity(Long userId, Long communityId);

    void updateCommunity(Long userId, Long communityId, CommunityRequestDto.UpdateDto updateDto);
}
