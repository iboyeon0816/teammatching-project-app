package com.sphere.demo.auth.jwt;

import com.sphere.demo.domain.User;
import com.sphere.demo.service.UserAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final static String ACCESS_TYPE = "Access";
    private final static String REFRESH_TYPE = "Refresh";

    private final JwtProperties jwtProperties;
    private final UserAuthService userAuthService;

    private String base64EncodedSecretKey;

    @PostConstruct
    public void init() {
        this.base64EncodedSecretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId) {
        return generateToken(userId, ACCESS_TYPE, jwtProperties.getAccessTokenExpiresIn());
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(userId, REFRESH_TYPE, jwtProperties.getRefreshTokenExpiresIn());
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(base64EncodedSecretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get("userId", Long.class);
        User user = userAuthService.findUser(userId);

        // TODO: 사용자 객체 ROLE(권한) 정보 추가 필요
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(user.getId(), null, roles);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(base64EncodedSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Long userId, String type, Integer tokenExpiresIn) {
        long current = System.currentTimeMillis();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(new Date(current))
                .setSubject(type)
                .setExpiration(new Date(
                        current + tokenExpiresIn * 1000
                ))
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }

}
