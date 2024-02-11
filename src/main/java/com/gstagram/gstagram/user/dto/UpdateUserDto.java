package com.gstagram.gstagram.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateUserDto {
    private String nickname;
    private String profileS3ImageUrl;
}
