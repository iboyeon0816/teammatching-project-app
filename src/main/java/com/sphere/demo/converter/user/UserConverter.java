package com.sphere.demo.converter.user;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.JoinDto;

public class UserConverter {
    public static User toUser(JoinDto joinDto) {
        return User.builder()
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .selfIntroduction(joinDto.getSelfIntroduction())
                .build();
    }
}
