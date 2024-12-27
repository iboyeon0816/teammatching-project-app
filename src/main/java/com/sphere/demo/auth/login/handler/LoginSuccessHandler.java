package com.sphere.demo.auth.login.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.auth.jwt.JwtUtils;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.user.RefreshTokenService;
import com.sphere.demo.web.dto.user.UserAuthResponseDto.LoginSuccessDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        String accessToken = jwtUtils.generateAccessToken(userId);
        String refreshToken = jwtUtils.generateRefreshToken(userId);
        refreshTokenService.saveRefreshToken(user, refreshToken);

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        String responseBody = createResponseBody(accessToken, refreshToken);
        response.getWriter().write(responseBody);
    }

    private String createResponseBody(String accessToken, String refreshToken) throws JsonProcessingException {
        ApiResponseDto<LoginSuccessDto> apiResponseDto = ApiResponseDto.onSuccess(new LoginSuccessDto(accessToken, refreshToken));
        return objectMapper.writeValueAsString(apiResponseDto);
    }

}
