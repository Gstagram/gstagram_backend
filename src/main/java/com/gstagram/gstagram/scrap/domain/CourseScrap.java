package com.gstagram.gstagram.scrap.domain;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CourseScrapId implements Serializable {
    private Long courseId;
    private Long userId;
}

@IdClass(CourseScrapId.class)
@Entity(name = "region_course_article_scrap")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseScrap {
    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
