package com.gstagram.gstagram.course.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{




}
