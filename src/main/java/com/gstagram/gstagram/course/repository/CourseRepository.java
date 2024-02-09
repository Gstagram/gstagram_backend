package com.gstagram.gstagram.course.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.dto.CourseSearchCond;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.user.domain.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, CourseQueryRepository{

    List<Course> findCourseByRegion(Region region);

    List<Course> findCourseByUser(User user);
}
