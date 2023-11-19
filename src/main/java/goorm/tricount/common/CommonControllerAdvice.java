package goorm.tricount.common;

import goorm.tricount.controller.ApiResponse;
import goorm.tricount.error.ApiErrorCode;
import goorm.tricount.exception.LoginFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleLoginFailureException() {

        return ApiResponse.fail(ApiErrorCode.ACCESS_DENIED.getCode(), ApiErrorCode.ACCESS_DENIED.getMessage());
    }

}
