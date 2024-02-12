package com.gstagram.gstagram.comment.application;

import com.gstagram.gstagram.comment.domain.Comment;
import com.gstagram.gstagram.comment.dto.CreateCommentDto;
import com.gstagram.gstagram.comment.dto.UpdateCommentDto;
import com.gstagram.gstagram.comment.repository.CommentRepository;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    @Override
    public void comment(String userId, CreateCommentDto createCommentDto) {
        User user = validateUser(userId);
        Course course = validateCourse(createCommentDto.getCourseId());
        commentRepository.save(Comment.builder()
                .user(user)
                .course(course)
                .content(createCommentDto.getContent())
                .build());
    }

    @Override
    public void deleteComment(String userId, Long commentId) {
        validateUser(userId);
        validateComment(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(String userId, UpdateCommentDto updateCommentDto) {
        User user = validateUser(userId);
        validateComment(updateCommentDto.getCommentId());
        commentRepository.save(Comment.builder()
                .id(updateCommentDto.getCommentId())
                .user(user)
                .content(updateCommentDto.getContent())
                .build());
    }
    @Override
    public int getCommentCount(Long courseId) {
        validateCourse(courseId);
        return commentRepository.countByCourseId(courseId);
    }

    @Override
    public List<Comment> getCommentList(Long courseId) {
        validateCourse(courseId);
        return commentRepository.findByCourseId(courseId);
    }

    @Override
    public List<Comment> getCommentListByUserId(String userId) {
        User user = validateUser(userId);
        return commentRepository.findByUser(user);
    }

    private User validateUser(String userId) {
        return userRepository.findByUuid(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
    }

    private Course validateCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new UserException(ResponseCode.COURSE_NOT_FOUND));
    }

    private void validateComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new UserException(ResponseCode.COMMENT_NOT_FOUND));
    }
}
