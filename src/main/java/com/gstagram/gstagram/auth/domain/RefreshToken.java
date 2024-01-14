package com.gstagram.gstagram.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class RefreshToken {
    @Id //redis에서 key값으로 사용하기 위해 id로 지정
    private String refreshToken;
    private String userId;
}
