package com.gstagram.gstagram.like.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.CourseException;
import com.gstagram.gstagram.common.exception.LikeException;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.like.domain.Like;
import com.gstagram.gstagram.like.repository.LikeRepository;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    @Override
    @Transactional
    public void like(String userId, Long courseId) {
        validateUser(userId);
        validateCourse(courseId);
        Like like = Like.builder()
                .userId(userId)
                .courseId(courseId)
                .build();
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void unlike(String userId, Long courseId) {
        validateUser(userId);
        validateCourse(courseId);
        validateLike(userId, courseId);
        likeRepository.deleteByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLiked(String  userId, Long courseId) {
        validateUser(userId);
        validateCourse(courseId);
        return likeRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public int getLikeCount(Long courseId) {
        validateCourse(courseId);
        return likeRepository.countByCourseId(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getLikeUserList(Long courseId) {
        validateCourse(courseId);
        List<Like> likeList = likeRepository.findAllByCourseId(courseId);
        return userRepository.getUsersByUuidIn(likeList.stream().map(Like::getUserId).toList());
    }

    private void validateUser(String userId) {
        if (!userRepository.existsByUuid(userId)) {
            log.info("존재하지 않는 유저입니다 by {}", userId);
            throw new UserException(ResponseCode.USER_NOT_FOUND);
        }
    }

    private void validateCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            log.info("존재하지 않는 코스입니다 by {}", courseId);
            throw new CourseException(ResponseCode.COURSE_NOT_FOUND);
        }
    }

    private void validateLike(String userId, Long courseId) {
        if (!likeRepository.existsByUserIdAndCourseId(userId, courseId)) {
            log.info("좋아요를 누르지 않은 코스입니다 by {}", courseId);
            throw new LikeException(ResponseCode.LIKE_NOT_FOUND);
        }
    }
}
