package com.gstagram.gstagram.place.application;

import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

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
    @DisplayName("update: Place 업데이트 테스트")
    public void changePlaceTest() {
        // given
        Long placeId = 1L;
        PlaceUpdateDTO placeUpdateDTO = PlaceUpdateDTO.builder().placeName("New Name").placeDescription("New Descriptin").build();

        Place place = Place.builder().id(placeId).placeName("Old Name").description("Old Description").build();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        // when
        Place updatedPlace = placeService.changePlace(placeId, placeUpdateDTO);

        // then
        verify(placeRepository).findById(placeId);
        assertEquals(placeUpdateDTO.getPlaceName(), updatedPlace.getPlaceName());
        assertEquals(placeUpdateDTO.getPlaceDescription(), updatedPlace.getDescription());
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