package com.sphere.demo.auth.bearer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.demo.apipayload.ApiResponseDto;
import com.sphere.demo.apipayload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class BearerAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String responseBody = createResponseBody();
        response.getWriter().write(responseBody);
    }

    private String createResponseBody() throws JsonProcessingException {
        ApiResponseDto<Void> apiResponseDto = ApiResponseDto.onFailure(ErrorStatus.TOKEN_INVALID, null);
        return objectMapper.writeValueAsString(apiResponseDto);
    }
}
