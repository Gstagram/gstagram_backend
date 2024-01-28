/*
package com.gstagram.gstagram.course.repository;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.dto.CourseSearchCond;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
class CourseRepositoryTest {

    private final User testUser = User.builder()
            .email("test12345@example.com")
            .username("행배")
            .password("example123")
            .createdDate(LocalDateTime.now())
            .lastConnectDate(LocalDateTime.now())
            .isUserAuthorized(true)
            .build();
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    UserRepository userRepository;

    //초기 데이터값 세팅 -> BeforeAll을 하게 되면 롤백이 적용 안되네..
    @BeforeEach
    void setUp() {
        userRepository.save(testUser);
        Region region1 = regionRepository.findByRegionName("경기도").get();

        City city1 = cityRepository.findByCityNameContaining("광명").get(0);
        Course course1 = Course.builder().courseName("안남시장1").description("hi1").region(region1).user(testUser).city(city1).createdTime(LocalDateTime.now().plusHours(1))
                .thumbNailUrl("123").build();
        City city2 = cityRepository.findByCityNameContaining("안산").get(0);
        Course course2 = Course.builder().courseName("안산시장2").description("hi1").region(region1).user(testUser).city(city2).createdTime(LocalDateTime.now().plusHours(-1))
                .thumbNailUrl("123").build();

        Region region2 = regionRepository.findByRegionName("경상남도").get();
        City city3 = cityRepository.findByCityNameContaining("통영").get(0);
        Course course3 = Course.builder().courseName("통영촌장1").description("hi1").region(region2).user(testUser).city(city3).createdTime(LocalDateTime.now().plusHours(1))
                .build();
        City city4 = cityRepository.findByCityNameContaining("진주").get(0);
        Course course4 = Course.builder().courseName("진주촌장2").description("hi1").region(region2).user(testUser).city(city4).createdTime(LocalDateTime.now().plusHours(-11))
                .build();

        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);
    }


    @Test
    @DisplayName("paging 적용 여부 확인")
    void paging_test() {
        //given
        CourseSearchCond cond = CourseSearchCond.builder().cityList(List.of(cityRepository.findByCityNameContaining("광명").get(0)))
                .regionList(List.of(regionRepository.findByRegionName("경상남도").get(), regionRepository.findByRegionName("경기도").get())).build();
        Pageable pageable1 = PageRequest.of(0, 2);
        Pageable pageable2 = PageRequest.of(1, 2);

        //when
        Page<Course> courses1 = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable1);
        List<Course> courseList1 = courses1.get().toList();
        Page<Course> courses2 = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable2);
        List<Course> courseList2 = courses2.get().toList();

        //then
        Assertions.assertThat(courseList1.size()).isEqualTo(2);
        Assertions.assertThat(courseList2.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("region 검색 여부")
    void region_test() {
        //given
        Region region1 = regionRepository.findByRegionName("경상남도").get();
        Region region2 = regionRepository.findByRegionName("경기도").get();
        CourseSearchCond cond = CourseSearchCond.builder()
                .regionList(List.of(regionRepository.findByRegionName("경상남도").get())).build();

        Pageable pageable1 = PageRequest.of(0, 3);

        //when
        Page<Course> courses1 = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable1);
        List<Course> courseList1 = courses1.get().toList();

        //then
        Assertions.assertThat(courseList1.size()).isEqualTo(2);
        Assertions.assertThat(courseList1.stream().map(Course::getRegion)).contains(region1);
        Assertions.assertThat(courseList1.stream().map(Course::getRegion)).doesNotContain(region2);
    }

    @Test
    @DisplayName("region + city 검색 여부")
    void courseQueryRepository() {
        //given
        Region region1 = regionRepository.findByRegionName("경상남도").get();
        City city2 = cityRepository.findByCityNameContaining("안산").get(0);
        CourseSearchCond cond = CourseSearchCond.builder().cityList(List.of(city2))
                .regionList(List.of(regionRepository.findByRegionName("경상남도").get())).build();

        Pageable pageable1 = PageRequest.of(0, 3);

        //when
        Page<Course> courses1 = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable1);
        List<Course> courseList1 = courses1.get().toList();

        //then
        Assertions.assertThat(courseList1.size()).isEqualTo(3);
        Assertions.assertThat(courseList1.stream().map(Course::getRegion)).contains(region1);
        Assertions.assertThat(courseList1.stream().map(Course::getCity)).contains(city2);
    }

    @Test
    @DisplayName("query_test 검색 여부")
    void title_query_test() {
        //given
        Region region1 = regionRepository.findByRegionName("경상남도").get();
        Region region2 = regionRepository.findByRegionName("경기도").get();
        CourseSearchCond cond = CourseSearchCond.builder()
                .regionList(List.of(region1, region2))
                .courseTitle("촌장").build();
        Pageable pageable1 = PageRequest.of(0, 4);

        //when
        Page<Course> courses1 = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable1);
        List<Course> courseList1 = courses1.get().toList();

        //then
        Assertions.assertThat(courseList1.size()).isEqualTo(2);
        Assertions.assertThat(courseList1.stream().map(Course::getRegion)).contains(region1);
        Assertions.assertThat(courseList1.stream().map(Course::getCourseName)).contains("통영촌장1");
        Assertions.assertThat(courseList1.stream().map(Course::getCourseName)).contains("진주촌장2");
    }

}*/
