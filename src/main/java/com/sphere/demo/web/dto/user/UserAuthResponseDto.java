package com.sphere.demo.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserAuthResponseDto {
    @Getter
    @AllArgsConstructor
    public static class LoginSuccessDto {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshSuccessDto {
        private String accessToken;
    }
}
