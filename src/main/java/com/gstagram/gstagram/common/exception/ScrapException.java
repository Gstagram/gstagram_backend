package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class ScrapException extends BaseException{
    public ScrapException(ResponseCode responseCode) {
        super(responseCode);
    }
}
