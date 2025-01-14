package com.penta.security.global;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.penta.security.global.exception.ErrorCode;
import com.penta.security.global.exception.ErrorCodeMapper;
import com.penta.security.global.exception.PasswordIncorrectException;
import com.penta.security.global.exception.RefreshTokenInvalidException;
import com.penta.security.global.exception.UserIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ErrorCodeMapperTest {

    public MethodArgumentNotValidException createMethodArgumentNotValidException() {
        MethodParameter parameter = null;
        BindingResult bindingResult = new DataBinder("objectName").getBindingResult();

        return new MethodArgumentNotValidException(parameter, bindingResult);
    }

    @Test
    void testGetErrorCodeWithMappedExceptions() {
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR,
            ErrorCodeMapper.getErrorCode(new RuntimeException("Internal Server Error")));
        assertEquals(ErrorCode.METHOD_ARGUMENT_INVALID,
            ErrorCodeMapper.getErrorCode(createMethodArgumentNotValidException()));
        assertEquals(ErrorCode.ACCESS_DENIED,
            ErrorCodeMapper.getErrorCode(new AuthorizationDeniedException("Access Denied")));
        assertEquals(ErrorCode.REFRESH_TOKEN_INVALID,
            ErrorCodeMapper.getErrorCode(new RefreshTokenInvalidException("Refresh Token Invalid")));
        assertEquals(ErrorCode.USERNAME_NOT_FOUND,
            ErrorCodeMapper.getErrorCode(new UsernameNotFoundException("Username Not Found")));
        assertEquals(ErrorCode.USER_ID_NOT_FOUND,
            ErrorCodeMapper.getErrorCode(new UserIdNotFoundException("User Id Not Found")));
        assertEquals(ErrorCode.PASSWORD_INCORRECT,
            ErrorCodeMapper.getErrorCode(new PasswordIncorrectException("Password Incorrect")));
    }

    @Test
    void testGetErrorCodeWithUnmappedException() {
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR,
            ErrorCodeMapper.getErrorCode(new IllegalStateException("Unmapped Exception")));
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR,
            ErrorCodeMapper.getErrorCode(new NullPointerException("NullPointer Test")));

        class CustomException extends Exception {
            public CustomException(String message) {
                super(message);
            }
        }

        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR,
            ErrorCodeMapper.getErrorCode(new CustomException("Custom Exception")));
    }
}
