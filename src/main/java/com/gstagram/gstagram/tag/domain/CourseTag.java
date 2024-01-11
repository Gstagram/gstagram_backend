package com.gstagram.gstagram.tag.domain;

import com.gstagram.gstagram.course.domain.Course;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CourseTagId implements Serializable {
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "tag_id")
    private Long tagId;
}

@IdClass(CourseTagId.class)
@Entity(name = "region_course_article_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseTag {
    @Id
    private Long courseId;

    @Id
    private Long tagId;

    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    @ManyToOne
    private Course course;

    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    @ManyToOne
    private Tag tag;
}
