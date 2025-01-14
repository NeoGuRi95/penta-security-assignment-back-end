package com.penta.security.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN),
    ACCESS_TOKEN_INVALID("ACCESS_TOKEN_INVALID", HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_INVALID("REFRESH_TOKEN_INVALID", HttpStatus.UNAUTHORIZED),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", HttpStatus.UNAUTHORIZED),
    USER_ID_NOT_FOUND("USER_ID_NOT_FOUND", HttpStatus.UNAUTHORIZED),
    METHOD_ARGUMENT_INVALID("METHOD_ARGUMENT_INVALID", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String name;

    private final HttpStatus httpStatus;

    public String toString() {
        return this.name;
    }

}
