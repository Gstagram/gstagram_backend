package com.gstagram.gstagram.course.application;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CourseServiceTest {


    @Mock
    PlaceRepository placeRepository;
    @Mock
    CourseRepository courseRepository;
    @InjectMocks
    CourseService courseService;


    @Test
    public void saveCourseWithPlaceTest() {
        // given
        Course course = Course.builder().build();
        Place place1 = Place.builder().build();
        Place place2 = Place.builder().build();
        List<Place> places = Arrays.asList(place1, place2);

        // when
        courseService.saveCourseWithPlace(course, places);


        // then
        Mockito.verify(courseRepository).save(course);
        Mockito.verify(placeRepository).saveAll(places);
    }

}