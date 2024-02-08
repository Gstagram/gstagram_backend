package com.gstagram.gstagram.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseUserDto {
    private String email;
    private String username;
    private String nickname;
    private String profileImageS3Url;

    public ResponseUserDto from(String email, String username, String nickname, String profileImageS3Url){
        return ResponseUserDto.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .profileImageS3Url(profileImageS3Url)
                .build();
    }
}
