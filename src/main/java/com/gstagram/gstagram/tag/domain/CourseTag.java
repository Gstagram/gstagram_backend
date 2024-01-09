package com.gstagram.gstagram.tag.domain;

import com.gstagram.gstagram.course.domain.Course;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CourseTagId implements Serializable {
    private Long courseId;
    private Long tagId;
}

@IdClass(CourseTagId.class)
@Entity(name = "region_course_article_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseTag {
    @Id
    @JoinColumn(name = "course_id")
    @ManyToOne
    private Course course;

    @Id
    @JoinColumn(name = "tag_id")
    @ManyToOne
    private Tag tag;
}
