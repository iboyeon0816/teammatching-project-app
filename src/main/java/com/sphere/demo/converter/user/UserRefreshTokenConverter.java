package com.sphere.demo.converter.user;

import com.sphere.demo.domain.User;
import com.sphere.demo.domain.UserRefreshToken;

public class UserRefreshTokenConverter {
    public static UserRefreshToken toUserRefreshToken(User user) {
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUser(user);
        return userRefreshToken;
    }
}
