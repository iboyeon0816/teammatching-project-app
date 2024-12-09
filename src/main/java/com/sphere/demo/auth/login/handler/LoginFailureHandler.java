package com.sphere.demo.auth.login.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String responseBody = createResponseBody();
        response.getWriter().write(responseBody);
    }

    private String createResponseBody() throws JsonProcessingException {
        ApiResponseDto<Void> apiResponseDto = ApiResponseDto.onFailure(ErrorStatus.LOGIN_FAILURE, null);
        return objectMapper.writeValueAsString(apiResponseDto);
    }
}
