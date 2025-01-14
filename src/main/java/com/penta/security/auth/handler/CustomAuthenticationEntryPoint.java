package com.penta.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.security.global.exception.ErrorCode;
import com.penta.security.user.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증(Authentication)되지 않은 사용자 요청에 대한 처리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException ex) throws IOException {
        log.error("[CustomAuthenticationEntryPointHandler] :: {}", ex.getMessage());
        log.error("[CustomAuthenticationEntryPointHandler] :: {}", request.getRequestURL());
        log.error("[CustomAuthenticationEntryPointHandler] :: 토큰 정보가 만료되었거나 존재하지 않습니다.");

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.ACCESS_TOKEN_INVALID,
            ex.getMessage());

        String responseBody = objectMapper.writeValueAsString(errorResponseDto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}