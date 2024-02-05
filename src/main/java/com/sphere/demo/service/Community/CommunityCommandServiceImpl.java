package com.sphere.demo.service.Community;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.CommunityConverter;
import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.CommunityRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.CommunityRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityCommandServiceImpl implements CommunityCommandService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    @Override
    public void create(Long userId, CommunityRequestDto.CreateDto createDto) {
        Community community = CommunityConverter.toCommunity(createDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        community.setUser(user);

        communityRepository.save(community);
    }

}
