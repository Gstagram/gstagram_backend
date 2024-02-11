package com.gstagram.gstagram.tag.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.CourseException;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.tag.domain.CourseTag;
import com.gstagram.gstagram.tag.domain.Tag;
import com.gstagram.gstagram.tag.repository.CourseTagRepository;
import com.gstagram.gstagram.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final CourseRepository courseRepository;
    private final CourseTagRepository courseTagRepository;

    @Override
    public void tagCourse(Long courseId, String tagName) {
        Course course = validateCourse(courseId);
        Optional<Tag> tag = tagRepository.findByTagName(tagName);
        tag.ifPresentOrElse(
                t -> {
                    courseTagRepository.save(CourseTag.builder()
                            .course(course)
                            .tag(t)
                            .build());
                },
                () -> {
                    Tag newTag = Tag.builder()
                            .tagName(tagName)
                            .build();
                    tagRepository.save(newTag);
                    courseTagRepository.save(CourseTag.builder()
                            .course(course)
                            .tag(newTag)
                            .build());
                });
    }

    @Override
    public void untagCourse(Long courseId, Long tagId) {
        validateCourse(courseId);
        validateTag(tagId);

        courseTagRepository.deleteByCourseIdAndTagId(courseId, tagId);
    }

    @Override
    public int getTagCount(Long tagId) {
       return courseTagRepository.countByTagId(tagId);
    }

    @Override
    public List<Tag> getAllTagList() {
        return tagRepository.findAll();
    }

    @Override
    public List<Course> getCourseListByTag(Long tagId) {
        validateTag(tagId);
        List<Long> courseIdList = courseTagRepository.findCourseIdByTagId(tagId);
        return courseRepository.findAllById(courseIdList);
    }

    private Course validateCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
    }

    private void validateTag(Long tagId) {
        tagRepository.findById(tagId).orElseThrow(() -> new CourseException(ResponseCode.TAG_NOT_FOUND));
    }
}
