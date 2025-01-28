package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.auth.jwt.JwtUtils;
import com.sphere.demo.converter.user.UserRefreshTokenConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.UserRefreshToken;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRefreshTokenRepository;
import com.sphere.demo.web.dto.user.UserAuthResponseDto.RefreshSuccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sphere.demo.auth.jwt.JwtUtils.REFRESH_TYPE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtils jwtUtils;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public RefreshSuccessDto refresh(String refreshToken) {
        validateToken(refreshToken);
        String accessToken = generateAccessToken(refreshToken);
        return new RefreshSuccessDto(accessToken);
    }

    public void saveRefreshToken(User user, String refreshToken) {
        UserRefreshToken userRefreshToken = userRefreshTokenRepository
                .findByUser(user)
                .orElseGet(() -> UserRefreshTokenConverter.toUserRefreshToken(user));

        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshTokenRepository.save(userRefreshToken);
    }

    private void validateToken(String refreshToken) {
        boolean exists = userRefreshTokenRepository.existsByRefreshToken(refreshToken);
        if (!exists) {
            throw new UserException(ErrorStatus.TOKEN_INVALID);
        }

        boolean isValid = jwtUtils.validate(refreshToken, REFRESH_TYPE);
        if (!isValid) {
            throw new UserException(ErrorStatus.TOKEN_INVALID);
        }
    }

    private String generateAccessToken(String refreshToken) {
        Authentication authentication = jwtUtils.getAuthentication(refreshToken);
        Long userId = (Long) authentication.getPrincipal();
        return jwtUtils.generateAccessToken(userId);
    }
}
