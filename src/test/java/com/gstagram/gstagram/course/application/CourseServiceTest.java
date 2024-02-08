package com.gstagram.gstagram.course.application;

import com.gstagram.gstagram.common.exception.CourseException;
import com.gstagram.gstagram.course.application.dto.request.CourseSearchDTO;
import com.gstagram.gstagram.course.application.dto.response.CourseWithPlaceDTO;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.presentation.dto.request.CourseFindCond;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

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
    @DisplayName("create: course 생성 테스트")
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

    @Test
    @DisplayName("read: course id값으로 찾기")
    public void findCourseTest() {
        // given
        Long courseId = 1L;
        Course course = Course.builder().id(courseId).build();

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course findCourse = courseService.findCourseById(courseId);
        // then

        Mockito.verify(courseRepository).findById(courseId);

        assertEquals(findCourse.getId(), course.getId());

    }

    @Test
    @DisplayName("read: course가 존재하지 않는 경우 테스트")
    public void findCourseTest_Course_Not_Found() {
        // given
        Long courseId = 1L;


        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // then
        assertThrows(CourseException.class, () -> {
            courseService.findCourseById(courseId);
        });
    }


    @Test
    @DisplayName("read: 주어진 courseId에 해당하는 Course와 Place를 찾는 테스트")
    public void findCourseWithPlaceByCourseIdTest() {
        // given
        Long courseId = 1L;
        Course course = Course.builder().build();
        List<Place> places = Arrays.asList(Place.builder().build(), Place.builder().build());

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(placeRepository.findAllByCourse(course)).thenReturn(places);

        // when
        CourseWithPlaceDTO courseWithPlaceDTO = courseService.findCourseWithPlaceByCourseId(courseId);

        // then
        Mockito.verify(courseRepository).findById(courseId);
        Mockito.verify(placeRepository).findAllByCourse(course);

        assertEquals(course, courseWithPlaceDTO.getCourse());
        assertEquals(places, courseWithPlaceDTO.getPlaceList());
    }


    @Test
    @DisplayName("update: course 수정 테스트")
    public void changeCourseTest() {
        // given
        Long courseId = 1L;
        String newName = "New Course Name";
        String newDescription = "New Description";
        Course course = Course.builder().id(courseId).courseName("Old Name").description("Old Description").build();

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        Course updatedCourse = courseService.changeCourse(courseId, newName, newDescription);

        // then
        Mockito.verify(courseRepository).findById(courseId);

        assertEquals(newDescription, updatedCourse.getDescription());
        assertEquals(newName, updatedCourse.getCourseName());
    }


    @Test
    @DisplayName("delete: course 삭제 테스트")
    public void deleteCourseTest() {
        // given
        Long courseId = 1L;
        Course course = Course.builder().id(courseId).build();

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        courseService.deleteCourse(courseId);

        // then
        Mockito.verify(courseRepository).findById(courseId);
        Mockito.verify(courseRepository).delete(course);
        Mockito.verify(placeRepository).deleteAllByCourse(course);
    }



}