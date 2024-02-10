package com.gstagram.gstagram.user.presentation;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.user.application.UserService;
import com.gstagram.gstagram.user.dto.ResponseUserDto;
import com.gstagram.gstagram.user.dto.UpdateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. User", description = "유저 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원 정보 조회
    @Operation(summary = "[프로필] 회원 기본정보 조회", description = "회원 기본정보를 조회합니다.")
    @GetMapping()
    public ApiResponse<ResponseUserDto> getUserInfo(@RequestHeader String accessToken) {
        return ApiResponse.success(ResponseCode.USER_READ_SUCCESS, ResponseUserDto.from(userService.getUserInfoByUuid(accessToken)));
    }

    //회원 정보 업데이트
    @Operation(summary = "[프로필] 회원 정보 업데이트", description = "회원 정보를 업데이트합니다.")
    @PutMapping()
    public ApiResponse<Void> updateUserInfo(@RequestHeader String accessToken, @RequestBody UpdateUserDto updateUserDto){
        String uuid = jwtTokenProvider.getUserPk(accessToken);
        userService.updateUserInfo(updateUserDto, uuid);
        return ApiResponse.success(ResponseCode.USER_UPDATE_SUCCESS, null);
    }

    //회원 정보 삭제
    @Operation(summary = "[프로필] 회원 정보 삭제", description = "회원 정보를 삭제합니다.")
    @DeleteMapping()
    public ApiResponse<Void> deleteUser(@RequestHeader String accessToken){
        String uuid = jwtTokenProvider.getUserPk(accessToken);
        userService.deleteUser(uuid);
        return ApiResponse.success(ResponseCode.USER_DELETE_SUCCESS, null);
    }

}
