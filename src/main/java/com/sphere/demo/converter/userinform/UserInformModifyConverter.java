package com.sphere.demo.converter.userinform;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserInformRequestDto;

public class UserInformModifyConverter {
    public static User toUserInform(UserInformRequestDto.ModifyDto request, User user) {
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        return user;
    }
}
