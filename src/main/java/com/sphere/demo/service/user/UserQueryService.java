package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.user.UserInfoResponseDto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public UserDetailDto getUserDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        return UserDetailDto.builder()
                .univEmail(user.getUnivEmail())
                .univName(user.getUnivName())
                .contactEmail(user.getContactEmail())
                .nickname(user.getNickname())
                .selfIntroduction(user.getSelfIntroduction())
                .imagePath(user.getImagePath())
                .build();
    }
}
