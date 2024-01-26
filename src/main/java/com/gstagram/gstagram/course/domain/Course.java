package com.gstagram.gstagram.course.domain;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City city;

    private String thumbNailUrl;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    public void changeCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
