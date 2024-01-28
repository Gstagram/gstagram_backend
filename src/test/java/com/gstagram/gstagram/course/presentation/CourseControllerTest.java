package com.gstagram.gstagram.course.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.city.application.CityService;
import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.course.application.CourseService;
import com.gstagram.gstagram.course.application.dto.request.CourseSearchDTO;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.presentation.dto.request.CourseCreateDTO;
import com.gstagram.gstagram.course.presentation.dto.request.CourseFindCond;
import com.gstagram.gstagram.course.presentation.dto.request.CourseUpdateDTO;
import com.gstagram.gstagram.course.presentation.dto.request.PlaceRequestDTO;
import com.gstagram.gstagram.place.application.PlaceService;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.region.application.RegionService;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser("test1@example.com")
class CourseControllerTest {

    private final User testUser = User.builder()
            .uuid(String.valueOf(UUID.randomUUID()))
            .email("test1@example.com")
            .username("행배")
            .password("example123")
            .createdDate(LocalDateTime.now())
            .lastConnectDate(LocalDateTime.now())
            .isUserAuthorized(true)
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RegionService regionService;
    @MockBean
    private CityService cityService;
    @MockBean
    private CourseService courseService;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private UserRepository userRepository;
    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        Region mockRegion1 = Region.builder().regionName("경상남도").build();
        Region mockRegion2 = Region.builder().regionName("경상북도").build();

        City city1 = City.builder().cityName("김해시").region(mockRegion1).build();
        City city2 = City.builder().cityName("창원시").region(mockRegion1).build();
        Course course = Course.builder().region(mockRegion1).city(city1).description("old Description").courseName("old Name").build();

        when(regionService.findByNameContaining("경상남도")).thenReturn(mockRegion1);
        when(regionService.findByNameContaining("경상북도")).thenReturn(mockRegion2);
        when(cityService.findByCityNameContaining("김해시")).thenReturn(city1);
        when(cityService.findByCityNameContaining("창원시")).thenReturn(city2);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(courseService.findCourseById(1L)).thenReturn(course);

    }

    @Test
    @DisplayName("createCourse: Course 생성 테스트")

    public void createCourseTest() throws Exception {
        // given
        PlaceRequestDTO placeRequestDTO = PlaceRequestDTO.builder()
                .placeName("김판호")
                .description("라이타 불 붙이던")
                .latitude(37.0).longitude(131.0)
                .sequence(0)
                .placeImagesURL(List.of("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")).build();

        CourseCreateDTO courseCreateDTO = CourseCreateDTO.builder()
                .courseName("무진여행기")
                .regionName("경상남도")
                .cityName("김해시")
                .courseDescription("김해 촌장")
                .thumbnailURL("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")
                .placeRequestDTOList(List.of(placeRequestDTO))
                .build();


        String content = objectMapper.writeValueAsString(courseCreateDTO);

        // when & then
        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.header.code").value(201))
                .andExpect(jsonPath("$.data").value(true)).andDo(print());

    }

    @Test
    @DisplayName("createCourse: Course 생성 - 지역 에러 검증")
    public void createCourseTest_regionError() throws Exception {
        // given
        // 경상북도 김해시 (전라북도 용인시의 개념으로 보면 됨)
        PlaceRequestDTO placeRequestDTO = PlaceRequestDTO.builder()
                .placeName("김판호")
                .description("라이타 불 붙이던")
                .latitude(37.0).longitude(131.0)
                .sequence(0)
                .placeImagesURL(List.of("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")).build();

        CourseCreateDTO courseCreateDTO = CourseCreateDTO.builder()
                .courseName("무진여행기")
                .regionName("경상북도")
                .cityName("김해시")
                .courseDescription("김해 촌장")
                .thumbnailURL("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")
                .placeRequestDTOList(List.of(placeRequestDTO))
                .build();


        String content = objectMapper.writeValueAsString(courseCreateDTO);

        // when & then
        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.header.code").value(400))
                .andExpect(jsonPath("$.data").value(false)).andDo(print());

    }


    @Test
    @DisplayName("updateCourse: Course 수정 테스트")
    void updateCourseTest() throws Exception {
        // given
        CourseUpdateDTO courseUpdateDTO = CourseUpdateDTO.builder()
                .courseId(1L)
                .courseName("new Name")
                .description("new Description")
                .build();
        // json작성
        String content = objectMapper.writeValueAsString(courseUpdateDTO);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.header.code").value(200));

        verify(courseService, times(1)).changeCourse(1L, courseUpdateDTO.getCourseName(), courseUpdateDTO.getDescription());

    }

    @Test
    @DisplayName("deleteCourse: Course 삭제 테스트")
    void deleteCourseTest() throws Exception {
        // given
        Long courseId = 1L;


        // when & then
        mockMvc.perform(delete("/api/course/delete/{courseId}", courseId))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.header.code").value(200)); // Adjust this based on your ApiResponse structure

        // Verify that the service method was called once
        verify(courseService, times(1)).deleteCourse(courseId);
    }


    @Test
    @DisplayName("addPlace: Course에 Place 추가 테스트")
    void addPlaceTest() throws Exception {
        // given
        Long courseId = 2L;
        PlaceRequestDTO placeRequestDTO = PlaceRequestDTO.builder()
                .placeName("김판호")
                .description("라이타 불 붙이던")
                .latitude(37.0).longitude(131.0)
                .sequence(0)
                .placeImagesURL(List.of("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")).build();


        String content = objectMapper.writeValueAsString(placeRequestDTO);

        // when & then
        mockMvc.perform(post("/api/course/add-place/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.header.code").value(200));

        verify(courseService, times(1)).findCourseById(courseId);
        verify(placeService, times(1)).savePlace(any(Place.class));
    }


    @Test
    public void testGetCourseByCond() throws Exception {
        //given
        List<Course> mockCourses = List.of(Course.builder()
                .region(Region.builder().build())
                .city(City.builder().build())
                .user(testUser)
                .courseName("123")
                .description("123")
                .createdTime(LocalDateTime.now())
                .build());


        when(courseService.findCourseWithCondOrderByDate(any(CourseSearchDTO.class), any(Pageable.class)))
                .thenReturn(mockCourses);
        CourseFindCond courseFindCond = CourseFindCond.builder().pageNumber(1).pageSize(1).build();

        //when && then
        mockMvc.perform(get("/api/course/findCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseFindCond)))
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.msg").value("course 조회 성공"));

        verify(courseService, times(1)).findCourseWithCondOrderByDate(any(CourseSearchDTO.class), any(Pageable.class));
    }
}
