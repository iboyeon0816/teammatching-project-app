package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;

public class UserInfoRequestDto {
    @Getter
    public static class UpdateDto {
        @Email
        private String contactEmail;
        private String selfIntroduction;
    }
}
