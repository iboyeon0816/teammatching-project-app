package com.sphere.demo.web.dto.user;

import lombok.Builder;
import lombok.Getter;

public class UserInfoResponseDto {

    @Getter
    @Builder
    public static class UserDetailDto {
        private String univEmail;
        private String univName;
        private String contactEmail;
        private String nickname;
        private String selfIntroduction;
        private String imagePath;
    }
}
