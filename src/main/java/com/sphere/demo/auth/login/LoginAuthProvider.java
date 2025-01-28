package com.sphere.demo.auth.login;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.auth.common.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class LoginAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String univEmail = authentication.getName();
        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(univEmail);

        String password = (String) authentication.getCredentials();
        if (!passwordEncoder.matches(password, userContext.getPassword())) {
            throw new BadCredentialsException(ErrorStatus.PASSWORD_NOT_MATCHED.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(
                userContext.getUser(),
                null,
                userContext.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
