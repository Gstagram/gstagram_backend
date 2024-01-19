package com.gstagram.gstagram.auth.presentation;

import com.gstagram.gstagram.auth.application.AuthService;
import com.gstagram.gstagram.auth.application.AuthServiceImpl;
import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/signup")
    public ApiResponse<Boolean> signUp(@RequestBody @Valid SignUpDto signUpDto){
        return ApiResponse.success(ResponseCode.USER_CREATE_SUCCESS, authService.signUp(signUpDto));
    }

    @PostMapping("/login")
    public ApiResponse<ResponseTokenDto> login(@RequestBody @Valid LoginReqDto loginReqDto){
        return ApiResponse.success(ResponseCode.USER_LOGIN_SUCCESS, authService.login(loginReqDto));
    }

    @PostMapping("/reissue")
    public ApiResponse<ResponseTokenDto> reissue(@RequestBody String refreshToken){
        return ApiResponse.success(ResponseCode.TOKEN_REISSUE_SUCCESS, authService.reissueToken(refreshToken));
    }
}
