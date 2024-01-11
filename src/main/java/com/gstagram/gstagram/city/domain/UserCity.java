package com.gstagram.gstagram.city.domain;

import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class UserCityId implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "city_id")
    private Long cityId;
}

@Entity(name = "user_city")
@Getter
@IdClass(UserCityId.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserCity {
    @Id
    private Long userId;

    @Id
    private Long cityId;

    @Column(name = "region_register_date", nullable = false)
    private String regionRegisteredDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uuid", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;
}
