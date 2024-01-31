package com.sphere.demo.auth.login.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.sphere.demo.apipayload.ApiResponse;
import com.sphere.demo.auth.jwt.JwtUtils;
import com.sphere.demo.domain.User;
import com.sphere.demo.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    private final JwtUtils jwtUtils;
    private final UserAuthService userAuthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        String accessToken = jwtUtils.generateAccessToken(userId);
        String refreshToken = jwtUtils.generateRefreshToken(userId);

        userAuthService.saveRefreshToken(user, refreshToken);

        String responseBody = createResponseBody(accessToken, refreshToken);

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(responseBody);
    }

    private String createResponseBody(String accessToken, String refreshToken) throws JsonProcessingException {
        LoginSuccessDto loginSuccessDto = new LoginSuccessDto(accessToken, refreshToken);
        ApiResponse<LoginSuccessDto> apiResponse = ApiResponse.onSuccess(loginSuccessDto);
        return objectMapper.writeValueAsString(apiResponse);
    }

    @Getter
    @AllArgsConstructor
    public static class LoginSuccessDto {
        private String accessToken;
        private String refreshToken;
    }
}
