package com.sphere.demo.service.Community;

import com.sphere.demo.domain.Community;
import com.sphere.demo.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommunityQueryServiceImpl implements CommunityQueryService {

    private final CommunityRepository communityRepository;


    @Override
    public List<Community> findAllCommunity() {
        return communityRepository.findAll();
    }

    public List<Community> getAllCommunity() {
        return findAllCommunity();
    }

    public boolean deleteCommunity(Long userId, Long communityId) {
        try {
            Optional<Community> optionalCommunity = communityRepository.findById(communityId);
            if (optionalCommunity.isPresent()) {
                Community community = optionalCommunity.get();
                if (community.getUserId().equals(userId)) {
                    communityRepository.deleteById(communityId);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
