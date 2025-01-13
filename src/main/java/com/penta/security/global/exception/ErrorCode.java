package com.penta.security.global.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

    PASSWORD_INCORRECT("PASSWORD_INCORRECT"),
    REFRESH_TOKEN_INVALID("REFRESH_TOKEN_INVALID"),
    ACCESS_TOKEN_INVALID("ACCESS_TOKEN_INVALID"),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND"),
    USER_ID_NOT_FOUND("USER_ID_NOT_FOUND"),
    METHOD_ARGUMENT_INVALID("METHOD_ARGUMENT_INVALID"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    ACCESS_DENIED("ACCESS_DENIED");

    private final String name;

    public String toString() {
        return this.name;
    }
}
