package com.gstagram.gstagram.like.domain;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class LikeId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;
}

@IdClass(LikeId.class)
@Entity(name = "course_like")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Like {
    @Id
    private Long userId;

    @Id
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;
}
