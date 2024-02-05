package com.sphere.demo.converter;

import com.sphere.demo.domain.UserRefreshToken;

public class UserRefreshTokenConverter {
    public static UserRefreshToken toUserRefreshToken(String refreshToken) {
        return UserRefreshToken.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
