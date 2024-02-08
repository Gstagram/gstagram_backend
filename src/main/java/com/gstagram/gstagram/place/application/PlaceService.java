package com.gstagram.gstagram.place.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.PlaceException;
import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.domain.PlaceImage;
import com.gstagram.gstagram.place.repository.PlaceImageRepository;
import com.gstagram.gstagram.place.repository.PlaceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceImageRepository placeImageRepository;


    /**
     * create
     *
     * @param place 저장할 place
     * @return 저장한 place
     */
    @Transactional
    public Place savePlace(Place place) {
        placeRepository.save(place);
        return place;
    }

    /**
     * read
     *
     * @param placeId 찾을 place의 Id(PK)값
     * @return 저장한 place
     * @throws PlaceException 주어진 ID를 가진 Place가 데이터베이스에 존재하지 않을 때 발생
     */
    public Place findPlaceById(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new PlaceException(ResponseCode.PLACE_NOT_FOUND));

    }

    /**
     * update
     *
     * @param placeUpdateDTO place의 필드값을 변경할 DTO (service 계층을 쓰기위한 dto)
     * @return 수정한 place
     * @throws PlaceException 주어진 ID를 가진 Place가 데이터베이스에 존재하지 않을 때 발생
     */
    @Transactional
    public Place changePlace(Long placeId, PlaceUpdateDTO placeUpdateDTO) {

        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceException(ResponseCode.PLACE_NOT_FOUND));
        updateEntity(placeUpdateDTO, place);
        return place;
    }

    /**
     * delete
     *
     * @param placeId 삭제할 place의 Id(PK)값
     * @throws PlaceException 주어진 ID를 가진 Place가 데이터베이스에 존재하지 않을 때 발생
     */
    @Transactional
    public void deletePlaceById(Long placeId) {

        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceException(ResponseCode.PLACE_NOT_FOUND));
        placeRepository.delete(place);

    }

    private void updateEntity(PlaceUpdateDTO placeUpdateDTO, Place place) {
        if (StringUtils.hasText(placeUpdateDTO.getPlaceName())) {
            place.changePlaceName(placeUpdateDTO.getPlaceName());
        }
        if (StringUtils.hasText(placeUpdateDTO.getPlaceDescription())) {
            place.changeDescription(placeUpdateDTO.getPlaceDescription());
        }
        if (placeUpdateDTO.getLatitude() != null && StringUtils.hasText(placeUpdateDTO.getLatitude().toString())) {
            place.changeLatitude(placeUpdateDTO.getLatitude());
        }
        if (placeUpdateDTO.getLongitude() != null && StringUtils.hasText(placeUpdateDTO.getLongitude().toString())) {
            place.changeLongitude(placeUpdateDTO.getLongitude());
        }
        if (placeUpdateDTO.getSequence() != null && StringUtils.hasText(placeUpdateDTO.getSequence().toString())) {
            place.changeSequence(placeUpdateDTO.getSequence());
        }
        if (placeUpdateDTO.getImageList() != null && !placeUpdateDTO.getImageList().isEmpty()) {
            placeUpdateDTO.getImageList().forEach((image) -> {

                PlaceImage placeImage = placeImageRepository.save(PlaceImage.builder().place(place).imageURL(image).build());
                place.addImage(placeImage);
            });

        }
    }


}
