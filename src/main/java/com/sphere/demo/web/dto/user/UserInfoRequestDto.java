package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserInfoRequestDto {
    @Getter
    public static class ModifyDto {
        @Email
        @NotBlank
        private String email;
        private String selfIntroduction;
    }
}
