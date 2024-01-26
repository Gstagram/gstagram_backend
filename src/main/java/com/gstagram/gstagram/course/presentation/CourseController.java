package com.gstagram.gstagram.course.presentation;

import com.gstagram.gstagram.city.application.CityService;
import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.course.application.CourseService;
import com.gstagram.gstagram.course.application.dto.CourseWithPlaceDTO;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.presentation.dto.request.CourseCreateDTO;
import com.gstagram.gstagram.course.presentation.dto.request.CourseUpdateDTO;
import com.gstagram.gstagram.course.presentation.dto.request.PlaceRequestDTO;
import com.gstagram.gstagram.place.application.PlaceService;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.region.application.RegionService;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "course", description = "Course 글 관련 login")
@Slf4j
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final RegionService regionService;
    private final CityService cityService;
    private final CourseService courseService;
    private final PlaceService placeService;
    // 임시로 해놓은거, 추 후에 userService 생성 시 활용
    private final UserRepository userRepository;

    /* @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "course + place 생성 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ApiResponse.class)))})*/
    @Operation(summary = "Course 저장", description = "course와 관련된 place들 저장하는 기능")
    @PostMapping("/create")
    public ApiResponse<Boolean> createCourse(@RequestBody @Valid CourseCreateDTO courseCreateDTO, BindingResult bindingResult,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        Region region = regionService.findByNameContaining(courseCreateDTO.getRegionName());
        City city = cityService.findByCityNameContaining(courseCreateDTO.getCityName());

        // validation
        // region과 city가 적법한가
        // 예를 들면 경상북도 용인시 이런 경우를 방지하기 위함
        if (isCityNotInRegion(region, city)) {
            //message.properties에 있는 error message
            bindingResult.reject("", "city와 region이 맞지 않습니다");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ApiResponse.fail(ResponseCode.REGION_CITY_INVALID, false);
        }

        //로그인 한 회원정보
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        Course course = CourseCreateDTO.toEntity(courseCreateDTO, region, city, user);

        List<Place> places = courseCreateDTO.getPlaceRequestDTOList().stream().map(
                placeRequestDTO -> {
                    return PlaceRequestDTO.toEntity(placeRequestDTO, course);
                }).toList();

        courseService.saveCourseWithPlace(course, places);
        return ApiResponse.success(ResponseCode.COURSE_CREATED_SUCCESS, true);
    }


    @Operation(summary = "course 수정", description = "course의 제목, description을 수정하는 api")
    @PutMapping("/update")
    public ApiResponse<Boolean> createCourse(@RequestBody @Valid CourseUpdateDTO courseUpdateDTO) {

        courseService.changeCourse(courseUpdateDTO.getCourseId(), courseUpdateDTO.getCourseName(), courseUpdateDTO.getDescription());
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, true);
    }

    @Operation(summary = "course 삭제", description = "course + 관련 place들 삭제")
    @DeleteMapping("/delete/{courseId}")
    public ApiResponse<Boolean> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, true);
    }


    @Operation(summary = "course에 place추가", description = "course에 place추가하는 api")
    @PostMapping("/add-place/{courseId}")
    public ApiResponse<Boolean> addPlace(@RequestBody @Valid PlaceRequestDTO placeRequestDTO, @PathVariable("courseId") Long courseId) {

        Course course = courseService.findCourseById(courseId);
        Place place = PlaceRequestDTO.toEntity(placeRequestDTO, course);
        placeService.savePlace(place);
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, true);
    }


    @Operation(summary = "course 조회", description = "course id로 course와 course 하위의 place를 조회")
    @GetMapping("/{courseId}")
    public ApiResponse<CourseWithPlaceDTO> getCourseWithPlace(@PathVariable("courseId") Long courseId) {
        CourseWithPlaceDTO courseWithPlace = courseService.findCourseWithPlaceByCourseId(courseId);
        return ApiResponse.success(ResponseCode.COURSE_ACCESS_SUCCESS, courseWithPlace);
    }


    private boolean isCityNotInRegion(Region region, City city) {
        return !city.getRegion().equals(region);
    }

}
