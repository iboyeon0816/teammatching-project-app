package com.sphere.demo.service.user;

import com.sphere.demo.converter.user.UserRefreshTokenConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.UserRefreshToken;
import com.sphere.demo.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public void saveRefreshToken(User user, String refreshToken) {
        UserRefreshToken userRefreshToken = user.getUserRefreshToken();

        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken);
            userRefreshTokenRepository.save(userRefreshToken);
        } else {
            UserRefreshToken newUserRefreshToken = UserRefreshTokenConverter.toUserRefreshToken(refreshToken);
            newUserRefreshToken.setUser(user);
            userRefreshTokenRepository.save(newUserRefreshToken);
        }
    }
}
