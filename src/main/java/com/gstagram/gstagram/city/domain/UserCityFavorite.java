package com.gstagram.gstagram.city.domain;

import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

class UserCityFavoriteId implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "city_id")
    private Long cityId;
}

@Entity(name = "city_favorite")
@Getter
@IdClass(UserCityFavoriteId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserCityFavorite {
    @Id
    private Long userId;

    @Id
    private Long cityId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;
}
