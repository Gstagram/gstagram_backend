package com.gstagram.gstagram.scrap.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.scrap.domain.Scrap;
import com.gstagram.gstagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndCourse(User user, Course course);
    boolean existsByUserAndCourse(User user, Course course);
    int countByCourse(Course course);
    List<Scrap> findAllByUser(User user);
}
