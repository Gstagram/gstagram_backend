package com.gstagram.gstagram.tag.repository;

import com.gstagram.gstagram.tag.domain.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {
    void deleteByCourseIdAndTagId(Long courseId, Long tagId);
    int countByTagId(Long tagId);

    List<Long> findCourseIdByTagId(Long tagId);
}
