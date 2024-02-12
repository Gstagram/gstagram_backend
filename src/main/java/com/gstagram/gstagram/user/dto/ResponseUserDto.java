package com.gstagram.gstagram.user.dto;

import com.gstagram.gstagram.user.domain.User;
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

    public static ResponseUserDto from(User user){
        return ResponseUserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .profileImageS3Url(user.getProfileImageS3Url())
                .build();
    }

}
