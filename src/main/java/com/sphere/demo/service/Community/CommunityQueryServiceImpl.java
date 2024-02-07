package com.sphere.demo.service.Community;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.CommunityConverter;
import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.CommunityException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.CommunityRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.CommunityRequestDto;
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
    private final UserRepository userRepository;


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

    public void updateCommunity(Long userId, Long communityId, CommunityRequestDto.UpdateDto updateDto) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(ErrorStatus.COMMUNITY_NOT_FOUND));

        if (community.getUserId().equals(userId)) {
//            Community updatedCommunity = CommunityConverter.updateCommunity(updateDto, community);
//            User user = userRepository.findById(userId)
//                    .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
//            updatedCommunity.setUser(user);

            System.out.println(updateDto.toString());
            community.setTitle(updateDto.getTitle());
            community.setBody(updateDto.getBody());

            communityRepository.save(community);
        } else {
            throw new CommunityException(ErrorStatus.USER_NOT_FOUND);
        }
    }

}
