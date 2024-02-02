package com.sphere.demo.converter;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserInformRequestDto;

import java.util.ArrayList;

public class UserInformModifyConverter {
    public static User toUserInform(UserInformRequestDto.ModifyDto request) {
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .school(request.getSchool())
                .major(request.getMajor())
                .userPositionList(new ArrayList<>())
                .userTechStackList(new ArrayList<>())
                .build();
    }
}
