package com.gstagram.gstagram.like.repository;

import com.gstagram.gstagram.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserIdAndCourseId(String userId, Long courseId);
    boolean existsByUserIdAndCourseId(String userId, Long courseId);
    int countByCourseId(Long courseId);
    List<Like> findAllByCourseId(Long courseId);
}
