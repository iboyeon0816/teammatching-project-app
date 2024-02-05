package com.sphere.demo.service.Community;

import com.sphere.demo.domain.Community;
import com.sphere.demo.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityQueryServiceImpl implements CommunityQueryService {

    private final CommunityRepository communityRepository;


    @Override
    public List<Community> findAllCommunity() {
        return communityRepository.findAll();
    }

    // 접근자 메서드 추가
    public List<Community> getAllCommunity() {
        return findAllCommunity();
    }
}
