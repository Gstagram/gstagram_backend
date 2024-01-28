package com.gstagram.gstagram.place.application;

import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.domain.PlaceImage;
import com.gstagram.gstagram.place.repository.PlaceImageRepository;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @Mock
    PlaceImageRepository placeImageRepository;

    @InjectMocks
    PlaceService placeService;


    @Test
    @DisplayName("create: Place 저장 테스트")
    public void savePlaceTest() {
        // given
        Place place = Place.builder().build();
        when(placeRepository.save(place)).thenReturn(place);

        // when
        Place savedPlace = placeService.savePlace(place);

        // then
        verify(placeRepository).save(place);
        assertEquals(place, savedPlace);
    }


    @Test
    @DisplayName("read: ID로 Place 찾기 테스트")
    public void findPlaceByIdTest() {
        // given
        Long placeId = 1L;
        Place place = Place.builder().id(placeId).build();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        // when
        Place foundPlace = placeService.findPlaceById(placeId);

        // then
        verify(placeRepository).findById(placeId);
        assertEquals(place, foundPlace);
    }

    @Test
    @DisplayName("update: Place 수정 테스트")
    public void changePlaceTest() {
        // given
        Long placeId = 1L;
        PlaceUpdateDTO placeUpdateDTO = PlaceUpdateDTO.builder().placeName("New Name").placeDescription("New Description")
                .imageList(List.of("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B81.jpeg"))
                .build();

        Place place = Place.builder().id(placeId).placeName("Old Name").description("Old Description").build();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(placeImageRepository.save(any(PlaceImage.class))).thenReturn(PlaceImage.builder()
                .imageURL("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B81.jpeg")
                .build());

        // when
        Place updatedPlace = placeService.changePlace(placeId, placeUpdateDTO);
        List<String> images = updatedPlace.getPlaceImageList().stream().map((placeImage -> placeImage.getImageURL())).toList();

        // then
        verify(placeRepository).findById(placeId);
        assertEquals(placeUpdateDTO.getPlaceName(), updatedPlace.getPlaceName());
        assertEquals(placeUpdateDTO.getPlaceDescription(), updatedPlace.getDescription());
        Assertions.assertThat("https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B81.jpeg")
                .isIn(images);
    }


    @Test
    @DisplayName("delete: ID로 Place 삭제 테스트")
    public void deletePlaceByIdTest() {
        // given
        Long placeId = 1L;
        Place place = Place.builder().id(placeId).build();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        Mockito.doNothing().when(placeRepository).delete(place);

        // when
        placeService.deletePlaceById(placeId);

        // then
        verify(placeRepository).findById(placeId);
        verify(placeRepository).delete(place);
    }

}