package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserRefreshTokenConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.UserRefreshToken;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRefreshTokenRepository;
import com.sphere.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;

    public void saveRefreshToken(User user, String refreshToken) {

        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByUser(user);

        if (userRefreshToken.isPresent()) {
            userRefreshToken.get().update(refreshToken);
        } else {
            UserRefreshToken newUserRefreshToken = UserRefreshTokenConverter.toUserRefreshToken(refreshToken);
            newUserRefreshToken.setUser(user);
            userRefreshTokenRepository.save(newUserRefreshToken);
        }
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }
}
