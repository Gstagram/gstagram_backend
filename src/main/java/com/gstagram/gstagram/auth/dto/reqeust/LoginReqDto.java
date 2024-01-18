package com.gstagram.gstagram.auth.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginReqDto {
    @NotNull
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotNull
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
