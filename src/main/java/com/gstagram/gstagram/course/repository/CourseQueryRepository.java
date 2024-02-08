package com.gstagram.gstagram.course.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.dto.CourseSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CourseQueryRepository {

    Page<Course> searchCourseBySearchConditionOrderByDate(CourseSearchCond courseSearchCond, Pageable pageable);
}
