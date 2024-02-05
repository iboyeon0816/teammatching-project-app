package com.sphere.demo.service.Community;

import com.sphere.demo.domain.Community;

import java.util.List;

public interface CommunityQueryService {
    List<Community> findAllCommunity();

    List<Community> getAllCommunity();
}
