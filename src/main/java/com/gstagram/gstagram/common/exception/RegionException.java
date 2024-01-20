package com.gstagram.gstagram.common.exception;

import com.gstagram.gstagram.common.api.ResponseCode;

public class RegionException extends BaseException {

    public RegionException(ResponseCode responseCode) {
        super(responseCode);
    }
}
