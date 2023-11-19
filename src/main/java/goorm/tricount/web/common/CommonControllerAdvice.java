package goorm.tricount.web.common;

import goorm.tricount.exception.*;
import goorm.tricount.web.apiresponse.ApiResponse;
import goorm.tricount.web.error.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleLoginFailureException() {

        return ApiResponse.fail(ApiErrorCode.NOT_ACCEPTABLE.getCode(), ApiErrorCode.NOT_ACCEPTABLE.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleResourceNotFoundException() {

        return ApiResponse.fail(ApiErrorCode.NOT_FOUND.getCode(), ApiErrorCode.NOT_FOUND.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleForbiddenException() {

        return ApiResponse.fail(ApiErrorCode.ACCESS_DENIED.getCode(), ApiErrorCode.ACCESS_DENIED.getMessage());
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleUserUnauthorizedException() {

        return ApiResponse.fail(ApiErrorCode.LOGIN_NEEDED.getCode(), ApiErrorCode.LOGIN_NEEDED.getMessage());
    }

    @ExceptionHandler(AlreadyJoinSettlementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAlreadyJoinSettlementException() {

        return ApiResponse.fail(ApiErrorCode.INVALID_INPUT_VALUE.getCode(), ApiErrorCode.INVALID_INPUT_VALUE.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleOtherException(Exception e) {
        log.info("서버 에러 발생", e);

        return ApiResponse.fail(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), ApiErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}
