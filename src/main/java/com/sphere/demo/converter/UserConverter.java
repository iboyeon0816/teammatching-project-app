package com.sphere.demo.converter;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.user.UserRequestDto.JoinDto;

public class UserConverter {
    public static User toUser(JoinDto joinDto) {
        return User.builder()
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .build();
    }
}
