package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class CourseException extends BaseException{
    public CourseException(ResponseCode responseCode) {
        super(responseCode);
    }
}
