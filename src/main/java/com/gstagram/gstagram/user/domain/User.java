package com.gstagram.gstagram.user.domain;

import com.gstagram.gstagram.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column()
    private String nickname;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_connect_date", nullable = false)
    private LocalDateTime lastConnectDate;

    @Column(name = "is_user_authorized",nullable = false)
    private Boolean isUserAuthorized;

    @Column(name = "profile_image_s3_url")
    private String profileImageS3Url;
}
