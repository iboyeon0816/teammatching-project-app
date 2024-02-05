package com.sphere.demo.converter;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserRequestDto.JoinDto;

import java.util.ArrayList;

public class UserConverter {
    public static User toUser(JoinDto joinDto, String encodedPassword) {
        return User.builder()
                .loginId(joinDto.getLoginId())
                .password(encodedPassword)
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .school(joinDto.getSchool())
                .major(joinDto.getMajor())
                .projectMatchList(new ArrayList<>())
                .userPositionList(new ArrayList<>())
                .userTechStackList(new ArrayList<>())
                .postList(new ArrayList<>())
                .build();
    }
}
