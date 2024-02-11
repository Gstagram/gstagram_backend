package com.gstagram.gstagram.course.application;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.CourseException;
import com.gstagram.gstagram.course.application.dto.request.CourseSearchDTO;
import com.gstagram.gstagram.course.application.dto.response.CourseWithPlaceDTO;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.repository.CourseRepository;
import com.gstagram.gstagram.course.repository.dto.CourseSearchCond;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import com.gstagram.gstagram.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class CourseService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final CourseRepository courseRepository;
    private final PlaceRepository placeRepository;

    /**
     * create
     *
     * @param course 저장할 course
     * @param places course 하위에 있는 place들을 저장할 변수
     * @return 저장한 course + places들, 이 들을 전달할 DTO 클래스
     */
    @Transactional
    public CourseWithPlaceDTO saveCourseWithPlace(Course course, List<Place> places) {

        courseRepository.save(course);
        placeRepository.saveAll(places);
        return CourseWithPlaceDTO.builder().course(course).placeList(places).build();
    }


    /**
     * create
     *
     * @param courseId 조회할 courseId
     * @return courseId로 찾은 course객체
     * @throws CourseException 주어진 ID를 가진 course가 데이터베이스에 존재하지 않을 때 발생
     */

    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
    }

    /**
     * create
     *
     * @param courseId 조회할 course의 ID(PK)값
     * @return course + places를 전달할 DTO 클래스 (places들은 course의 하위 값)
     * @throws CourseException 주어진 ID를 가진 course가 데이터베이스에 존재하지 않을 때 발생
     */
    public CourseWithPlaceDTO findCourseWithPlaceByCourseId(Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
        List<Place> places = placeRepository.findAllByCourse(course);
        return CourseWithPlaceDTO.builder().course(course).placeList(places).build();
    }


    /**
     * update
     *
     * @param courseId    수정할 course의 ID(PK)값
     * @param courseName  수정할 course의 이름(empty ok)
     * @param description 수정할 course의 description(empty ok)
     * @return 수정한 course값
     * @throws CourseException 주어진 ID를 가진 course가 데이터베이스에 존재하지 않을 때 발생
     */
    @Transactional
    public Course changeCourse(Long courseId, String courseName, String description) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));
        if (StringUtils.hasText(courseName)) {
            course.changeCourseName(courseName);
        }
        if (StringUtils.hasText(description)) {
            course.changeDescription(description);
        }
        return course;
    }

    /**
     * delete
     * 삭제시 course뿐만 아니라 연관된 place들도 삭제
     *
     * @param courseId 삭제할 course의 id(PK)값
     * @throws CourseException 주어진 ID를 가진 course가 데이터베이스에 존재하지 않을 때 발생
     */
    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException(ResponseCode.COURSE_NOT_FOUND));

        courseRepository.delete(course);
        placeRepository.deleteAllByCourse(course);

    }


    /**
     * SEARCH
     * 조건에 맞는 course들 조회
     *
     * @param courseSearchDTO 조회할 course 조건 (city, region, 제목)
     * @param pageable        course의 paging을 위한 parameter
     * @return 조회된 course entity를 return
     */
    //조건 + paging 숫자
    public List<Course> findCourseWithCondOrderByDate(CourseSearchDTO courseSearchDTO, Pageable pageable) {
        List<Region> regions = new ArrayList<>();
        fetchRegions(courseSearchDTO, regions);
        List<City> cities = new ArrayList<>();
        fetchCities(courseSearchDTO, cities);
        CourseSearchCond cond = CourseSearchCond.builder().courseTitle(courseSearchDTO.getCourseTitle())
                .cityList(cities)
                .regionList(regions).build();
        Page<Course> courses = courseRepository.searchCourseBySearchConditionOrderByDate(cond, pageable);
        return courses.get().toList();

    }
/*

    public List<Course> findCourseWithRegion(Region region){

        List<Course> courseList = courseRepository.findCourseByRegion(region);

    }
*/
    public List<Course> findCourseWithRegion(Region region){

        return courseRepository.findCourseByRegion(region);
    }
    public List<Course> findCourseByUser(User user){
        return courseRepository.findCourseByUser(user);
    }



    private void fetchRegions(CourseSearchDTO courseSearchDTO, List<Region> regions) {
        courseSearchDTO.getRegionNameList().forEach(s -> {
            List<Region> regions1 = regionRepository.findRegionByRegionNameContaining(s);
            if (!regions.isEmpty()) {
                regions.add(regions1.get(0));
            }
        });
    }

    private void fetchCities(CourseSearchDTO courseSearchDTO, List<City> cities) {
        courseSearchDTO.getRegionNameList().forEach(s -> {
            List<City> cities1 = cityRepository.findByCityNameContaining(s);
            if (!cities1.isEmpty()) {
                cities.add(cities1.get(0));
            }
        });
    }


}
