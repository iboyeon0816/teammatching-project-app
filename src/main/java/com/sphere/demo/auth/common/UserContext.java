package com.sphere.demo.auth.common;

import com.sphere.demo.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserContext extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLoginId(), user.getPassword(), authorities);
        this.user = user;
    }
}
