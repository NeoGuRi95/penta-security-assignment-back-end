package com.penta.security.global.exception.handler;

import com.penta.security.global.exception.ErrorCode;
import com.penta.security.global.exception.PasswordIncorrectException;
import com.penta.security.global.exception.RefreshTokenInvalidException;
import com.penta.security.global.exception.UserIdNotFoundException;
import com.penta.security.user.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private ResponseEntity<ErrorResponseDto> buildErrorResponseEntity(ErrorCode errorCode,
        String message) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, message);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    /**
     * 모든 예외 처리
     *
     * @param ex 발생한 예외
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException(RuntimeException ex) {
        log.error("Internal Server Error occurred", ex);

        return buildErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
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
        log.error("Method Argument Not Valid Exception occurred", ex);

        ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_INVALID;

        ErrorResponseDto body = new ErrorResponseDto(errorCode,
            "요청 데이터가 유효하지 않습니다. validErrors 필드를 확인하세요.");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            body.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }

    /**
     * 접근 권한 예외 처리
     *
     * @param ex AuthorizationDeniedException
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDeniedException(
        AuthorizationDeniedException ex) {
        log.error("Authorization Denied Exception occurred", ex);

        return buildErrorResponseEntity(ErrorCode.ACCESS_DENIED, ex.getMessage());
    }

    /**
     * Refresh Token 유효성 검증 예외 처리
     *
     * @param ex RefreshTokenInvalidException
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(RefreshTokenInvalidException.class)
    public ResponseEntity<ErrorResponseDto> handleRefreshTokenInvalidException(
        RefreshTokenInvalidException ex) {
        log.error("Refresh Token Invalid Exception occurred", ex);

        return buildErrorResponseEntity(ErrorCode.REFRESH_TOKEN_INVALID, ex.getMessage());
    }

    /**
     * Access Token 회원 ID 찾지 못한 경우 예외 처리
     *
     * @param ex UsernameNotFoundException
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameNotFoundException(
        UsernameNotFoundException ex) {
        log.error("Username Not Found Exception occurred", ex);

        return buildErrorResponseEntity(ErrorCode.USERNAME_NOT_FOUND, ex.getMessage());
    }

    /**
     * 로그인에서 회원 ID 찾지 못한 경우 예외 처리
     *
     * @param ex UserIdNotFoundException
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserIdNotFoundException(
        UserIdNotFoundException ex) {
        log.error("User Id Not Found Exception occurred", ex);

        return buildErrorResponseEntity(ErrorCode.USER_ID_NOT_FOUND, ex.getMessage());
    }

    /**
     * 비밀번호 불일치 예외 처리
     *
     * @param ex PasswordIncorrectException
     * @return ErrorResponse 응답
     */
    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordIncorrectException(
        PasswordIncorrectException ex) {
        log.error("Password Incorrect Exception occurred", ex);

        return buildErrorResponseEntity(ErrorCode.PASSWORD_INCORRECT, ex.getMessage());
    }
}