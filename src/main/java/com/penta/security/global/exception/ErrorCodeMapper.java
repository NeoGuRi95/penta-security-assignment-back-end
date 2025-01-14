package com.penta.security.global.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ErrorCodeMapper {

    private static final Map<Class<? extends Exception>, ErrorCode> exceptionToErrorCodeMap = new HashMap<>();

    static {
        exceptionToErrorCodeMap.put(RuntimeException.class, ErrorCode.INTERNAL_SERVER_ERROR);
        exceptionToErrorCodeMap.put(MethodArgumentNotValidException.class,
            ErrorCode.METHOD_ARGUMENT_INVALID);
        exceptionToErrorCodeMap.put(AuthorizationDeniedException.class, ErrorCode.ACCESS_DENIED);
        exceptionToErrorCodeMap.put(RefreshTokenInvalidException.class,
            ErrorCode.REFRESH_TOKEN_INVALID);
        exceptionToErrorCodeMap.put(UsernameNotFoundException.class, ErrorCode.USERNAME_NOT_FOUND);
        exceptionToErrorCodeMap.put(UserIdNotFoundException.class, ErrorCode.USER_ID_NOT_FOUND);
        exceptionToErrorCodeMap.put(PasswordIncorrectException.class, ErrorCode.PASSWORD_INCORRECT);
    }

    public static ErrorCode getErrorCode(Exception exception) {
        return exceptionToErrorCodeMap.getOrDefault(exception.getClass(),
            ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
