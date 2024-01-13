package com.gstagram.gstagram.region.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Region {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Column(name = "user_count", nullable = false)
    private int userCount;

    @Builder
    public Region(Long id, String regionName) {
        this.id = id;
        this.regionName = regionName;
    }

    // region user 수 증가용
    // only 한 명만 증가 되도록
    public void addUserCount() {
        this.userCount ++;
    }
}
