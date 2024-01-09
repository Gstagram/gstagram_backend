package com.gstagram.gstagram.region.domain;

import com.gstagram.gstagram.config.JpaAuditingConfig;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long regionId;

    @Column(nullable = false)
    private String regionName;

    @Column(nullable = false)
    private int userCount;
}
