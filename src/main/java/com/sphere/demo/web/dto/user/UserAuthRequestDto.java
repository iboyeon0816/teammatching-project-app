package com.sphere.demo.web.dto.user;

import com.sphere.demo.validation.annotation.NotDuplicatedNickname;
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
        @NotDuplicatedNickname
        private String nickname;
    }

    @Getter
    public static class LoginDto {
        private String email;
        private String password;
    }
}
