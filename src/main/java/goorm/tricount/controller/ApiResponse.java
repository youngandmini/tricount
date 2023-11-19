package goorm.tricount.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.tricount.error.ApiErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> implements Serializable {

    private ApiResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    public static <T> ApiResponse<T> ok(T result) {
        return new ApiResponse<>(new ApiResponseStatus(ApiErrorCode.OK.getCode(), ApiErrorCode.OK.getMessage()), result);
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(new ApiResponseStatus(code, message));
    }

    public ApiResponse(ApiResponseStatus status) {
        this.status = status;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiResponseStatus implements Serializable {
        private int code;
        private String message;
    }
}
