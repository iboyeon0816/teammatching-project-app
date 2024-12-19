package com.sphere.demo.auth.jwt;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.UserException;
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

    public final static String ACCESS_TYPE = "Access";
    public final static String REFRESH_TYPE = "Refresh";

    private final JwtProperties jwtProperties;

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

    public boolean validate(String token, String expectedType) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(base64EncodedSecretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String type = claims.getSubject();
            if (!expectedType.equals(type)) {
                throw new UserException(ErrorStatus.TOKEN_INVALID);
            }

            return true;
        } catch (Exception e) {
            log.error("[JwtUtils][validate] 인증 에러: ", e);
            return false;
        }
    }


    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get("userId", Long.class);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(userId, null, roles);
    }

    private String generateToken(Long userId, String type, Integer tokenExpiresIn) {
        long current = System.currentTimeMillis();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(new Date(current))
                .setSubject(type)
                .setExpiration(new Date(
                        current + tokenExpiresIn * 1000L
                ))
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(base64EncodedSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
