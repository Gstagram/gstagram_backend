package com.gstagram.gstagram.auth.application;

import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;

public interface AuthService {
    boolean signUp(SignUpDto signUpDto);

    ResponseTokenDto login(LoginReqDto loginReqDto);

    ResponseTokenDto reissueToken(String refreshToken);
}
