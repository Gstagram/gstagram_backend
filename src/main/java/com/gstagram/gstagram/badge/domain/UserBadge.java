package com.gstagram.gstagram.badge.domain;

import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class UserBadgeId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "badge_id")
    private Long badgeId;
}

@Entity(name = "user_badge")
@IdClass(UserBadgeId.class)
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBadge {
    @Id
    private Long userId;

    @Id
    private Long badgeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "badge_id", insertable = false, updatable = false)
    private Badge badge;

    @CreatedDate
    @Column(name = "get_date", nullable = false)
    private LocalDateTime getDate;
}
