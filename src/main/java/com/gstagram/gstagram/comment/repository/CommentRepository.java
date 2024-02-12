package com.gstagram.gstagram.comment.repository;

import com.gstagram.gstagram.comment.domain.Comment;
import com.gstagram.gstagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByCourseId(Long courseId);
    List<Comment> findByCourseId(Long courseId);
    List<Comment> findByUser(User user
    );
}
