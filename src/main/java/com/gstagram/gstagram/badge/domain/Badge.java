package com.gstagram.gstagram.badge.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "badge_image", nullable = false)
    private String badgeImage;

    @Column(name = "badge_title", nullable = false)
    private String badgeTitle;

    @Column(nullable = false)
    private String description;
}
