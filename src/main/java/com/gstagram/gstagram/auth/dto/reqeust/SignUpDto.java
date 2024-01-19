package com.gstagram.gstagram.auth.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignUpDto {
    @NotNull
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotNull
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    private String nickname;

    @NotNull
    @NotBlank(message = "프로필 사진 URL을 입력해주세요.")
    private String profileS3ImageUrl;

    @NotNull(message = "유저 인증 여부를 입력해주세요.")
    private boolean userAuthorized;
}
