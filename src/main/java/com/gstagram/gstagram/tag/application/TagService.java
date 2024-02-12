package com.gstagram.gstagram.tag.application;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.tag.domain.Tag;
import com.gstagram.gstagram.tag.dto.ResponseTagDto;

import java.util.List;

public interface TagService {
    void tagCourse(Long courseId, String tagName);

    void untagCourse(Long courseId, Long tagId);
    int getTagCount(Long tagId);
    List<Tag> getAllTagList();
    List<Course> getCourseListByTag(Long tagId);
}
