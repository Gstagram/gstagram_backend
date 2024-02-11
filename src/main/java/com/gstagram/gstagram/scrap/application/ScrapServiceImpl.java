package com.gstagram.gstagram.scrap.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.CourseException;
import com.gstagram.gstagram.common.exception.ScrapException;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.scrap.domain.Scrap;
import com.gstagram.gstagram.scrap.repository.ScrapRepository;
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
public class ScrapServiceImpl implements ScrapService{
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    @Override
    @Transactional
    public void scrap(String userId, Long courseId) {
        User user = validateUser(userId);
        Course course = validateCourse(courseId);
        scrapRepository.save(Scrap.builder()
                .user(user)
                .course(course)
                .build());
    }

    @Override
    @Transactional
    public void unscrap(String userId, Long courseId) {
        User user = validateUser(userId);
        Course course = validateCourse(courseId);
        Scrap scrap = scrapRepository.findByUserAndCourse(user, course).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
        scrapRepository.delete(scrap);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isScrapped(String userId, Long courseId) {
        User user = validateUser(userId);
        Course course = validateCourse(courseId);
        return scrapRepository.existsByUserAndCourse(user, course);
    }

    @Override
    @Transactional(readOnly = true)
    public int getScrapCount(Long courseId) {
        Course course = validateCourse(courseId);
        return scrapRepository.countByCourse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getScrappedCourseList(String userId) {
        User user = validateUser(userId);
        List<Scrap> scrapList = scrapRepository.findAllByUser(user);
        return scrapList.stream().map(Scrap::getCourse).toList();
    }

    private User validateUser(String userId) {
        return userRepository.findByUuid(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
    }

    private Course validateCourse(Long courseId) {
       return courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
    }

    private void validateScrap(String userId, Long courseId) {
        User user = userRepository.findByUuid(userId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));

        if (!scrapRepository.existsByUserAndCourse(user, course)) {
            throw new ScrapException(ResponseCode.SCRAP_NOT_FOUND);
        }
    }
}
