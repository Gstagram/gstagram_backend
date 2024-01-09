package com.gstagram.gstagram.region.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long regionId;

    @Column(nullable = false)
    private String regionName;

    @Column(nullable = false)
    private int userCount;
}
