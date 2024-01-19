package com.gstagram.gstagram.common.api;

import com.gstagram.gstagram.booklet.controller.dto.response.BookletResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private ApiHeader header;
    private T data;
    private String msg;

    private static final int SUCCESS = 200;

    private ApiResponse(ApiHeader header, T data, String msg) {
        this.header = header;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(new ApiHeader(SUCCESS, "SUCCESS"), data, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()), data, responseCode.getMessage());
    }


}