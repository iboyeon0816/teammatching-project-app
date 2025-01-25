package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserAuthRequestDto {
    @Getter
    public static class JoinDto {
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String nickname;

        private String selfIntroduction;
    }

    @Getter
    public static class LoginDto {
        private String email;
        private String password;
    }

    @Getter
    public static class RefreshDto {
        @NotBlank
        private String refreshToken;
    }
}
