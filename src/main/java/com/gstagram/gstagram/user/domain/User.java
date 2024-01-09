package com.gstagram.gstagram.user.domain;

import com.gstagram.gstagram.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "is_user_authorized",nullable = false)
    private Boolean isUserAuthorized;

    @Column(name = "profile_image_s3_url", nullable = true)
    private String profileImageS3Url;
}
