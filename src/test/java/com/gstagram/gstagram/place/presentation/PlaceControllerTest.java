package com.gstagram.gstagram.place.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.place.application.PlaceService;
import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.presentation.dto.request.PlaceUpdateRequestDTO;
import com.gstagram.gstagram.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PlaceController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser("test1@example.com")
class PlaceControllerTest {

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
    private PlaceService placeService;

    @Test
    @DisplayName("updatePlace 테스트")
    void updatePlaceTest() throws Exception {
        //given
        Long placeId = 1L;
        PlaceUpdateRequestDTO requestDTO = PlaceUpdateRequestDTO.builder()
                .placeDescription("new placeDescription")
                .placeName("new PlaceName")
                .latitude(37.0)
                .longitude(127.0)
                .sequence(11)
                .imageList(List.of("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B81.jpeg")).build();

        Place place = Place.builder()
                .id(placeId)
                .placeName("old placeName")
                .description("old description")
                .build();

        when(placeService.findPlaceById(placeId)).thenReturn(place);

        //when && then
        mockMvc.perform(put("/api/course/place/update/{placeId}", placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(jsonPath("$.data").value(true));


        verify(placeService, times(1)).changePlace(eq(placeId), any(PlaceUpdateDTO.class));

    }


    @Test
    @DisplayName("find Place 테스트")
    void findPlaceTest() throws Exception {
        Long placeId = 1L;
        Place place = Place.builder().id(placeId).course(Course.builder().id(11L).build()).build();


        when(placeService.findPlaceById(placeId)).thenReturn(place);

        mockMvc.perform(get("/api/course/place/find/{placeId}", placeId))
                .andExpect(jsonPath("$.data").isNotEmpty()).andDo(print());

        verify(placeService, times(1)).findPlaceById(placeId);
    }


    @Test
    @DisplayName("Place 삭제 테스트")
    void deletePlaceTest() throws Exception {
        Long placeId = 1L;

        doNothing().when(placeService).deletePlaceById(placeId);
        mockMvc.perform(delete("/api/course/place/delete/{placeId}", placeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(true));

        verify(placeService).deletePlaceById(placeId);
    }

}