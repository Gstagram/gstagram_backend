package com.gstagram.gstagram.scrap.application;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.scrap.domain.Scrap;

import java.security.cert.CertPath;
import java.util.List;

public interface ScrapService {
    void scrap(String userId, Long courseId);
    void unscrap(String userId, Long courseId);
    boolean isScrapped(String userId, Long courseId);
    int getScrapCount(Long courseId);
    List<Course> getScrappedCourseList(String userId);
}
