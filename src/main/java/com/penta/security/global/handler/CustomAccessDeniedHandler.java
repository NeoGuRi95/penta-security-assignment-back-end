package com.penta.security.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.security.global.exception.ErrorCode;
import com.penta.security.global.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 인증된 사용자는 맞으나 권한(Authorization)이 부족하여 액세스가 거부된 요청에 대한 처리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException ex) throws IOException {
        log.error("[CustomAccessDeniedHandler] :: {}", ex.getMessage());
        log.error("[CustomAccessDeniedHandler] :: {}", request.getRequestURL());
        log.error("[CustomAccessDeniedHandler] :: 해당 권한으로는 접근할 수 없습니다.");

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.ACCESS_DENIED,
            ex.getMessage());

        String responseBody = objectMapper.writeValueAsString(errorResponseDto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}