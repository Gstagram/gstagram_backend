package com.gstagram.gstagram.city.domain;

import com.gstagram.gstagram.region.domain.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @JoinColumn(name = "region_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Region region;

    @Builder
    public City(Long id, String cityName, Region region) {
        this.id = id;
        this.cityName = cityName;
        this.region = region;
    }
}
