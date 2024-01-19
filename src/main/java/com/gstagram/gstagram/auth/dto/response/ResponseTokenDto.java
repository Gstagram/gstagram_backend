package com.gstagram.gstagram.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseTokenDto {
    private String accessToken;
    private String refreshToken;
}
