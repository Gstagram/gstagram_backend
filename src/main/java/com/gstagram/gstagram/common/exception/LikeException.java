package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class LikeException extends BaseException{
    public LikeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
