package com.gstagram.gstagram.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiHeader {

    private int code;
    private String message;
}