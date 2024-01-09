package com.gstagram.gstagram.badge.domain;

import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class UserBadgeId implements Serializable {
    private Long userId;
    private Long badgeId;
}

@Entity(name = "user_badge")
@IdClass(UserBadgeId.class)
@EntityListeners(AuditingEntityListener.class)
public class UserBadge {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @CreatedDate
    @Column(name = "get_date", nullable = false)
    private LocalDateTime getDate;
}
