package com.penta.security.global.exception.handler;

import com.penta.security.global.exception.ErrorCode;
import com.penta.security.global.exception.ErrorCodeMapper;
import com.penta.security.global.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
        HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR,
            ex.getMessage());

        return ResponseEntity.status(statusCode).body(errorResponseDto);
    }

    private static void loggingException(Exception ex, ErrorCode errorCode) {
        log.error("Error occurred: [{}] - Message: {}", errorCode.getName(), ex.getMessage());
    }

    /**
     * 모든 예외 처리
     *
     * @param ex 발생한 예외
     * @return ErrorResponse 응답d
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException(RuntimeException ex) {
        ErrorCode errorCode = ErrorCodeMapper.getErrorCode(ex);

        loggingException(ex, errorCode);

        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, ex.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    /**
     * 유효성 검사 예외 처리
     *
     * @param ex      MethodArgumentNotValidException
     * @param headers 헤더
     * @param status  상태
     * @param request 요청
     * @return ErrorResponse 응답
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        ErrorCode errorCode = ErrorCodeMapper.getErrorCode(ex);

        loggingException(ex, errorCode);

        ErrorResponseDto body = new ErrorResponseDto(errorCode,
            "요청 데이터가 유효하지 않습니다. validErrors 필드를 확인하세요.");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            body.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }
}