package com.gstagram.gstagram.course.application;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final PlaceRepository placeRepository;

    /**
     *
     */
    @Transactional
    public void saveCourseWithPlace(Course course, List<Place> places) {

        courseRepository.save(course);
        placeRepository.saveAll(places);


    }
}
