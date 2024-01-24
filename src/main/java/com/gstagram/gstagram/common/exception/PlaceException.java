package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class PlaceException extends BaseException{
    public PlaceException(ResponseCode responseCode) {
        super(responseCode);
    }
}