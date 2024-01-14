package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class UserException extends BaseException{
    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
