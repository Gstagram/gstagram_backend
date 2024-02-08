package com.gstagram.gstagram.course.presentation.dto.request;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.domain.PlaceImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "course안에 있는 place 게시물 리스트 요청 DTO")
@Builder
@AllArgsConstructor
public class PlaceRequestDTO {
    @NotBlank
    @Schema(description = "place 이름")
    String placeName;
    @NotBlank
    @Schema(description = "place에 대한 설명")
    String description;
    @Schema(description = "위도")
    Double latitude;
    @Schema(description = "경도")
    Double longitude;


    @Schema(description = "순서 -> 추후에 작성정보 이런걸로 바꾸고 싶으면 요청하십쇼")
    Integer sequence;

    @Schema(description = "place에 넣을 이미지들")
    List<String> placeImagesURL;

    public static Place toEntity(PlaceRequestDTO placeRequestDTO, Course course) {
        Place place = Place.builder()
                .placeName(placeRequestDTO.getPlaceName())
                .description(placeRequestDTO.getDescription())
                .latitude(placeRequestDTO.getLatitude())
                .longitude(placeRequestDTO.getLongitude())
                .course(course)
                .sequence(placeRequestDTO.getSequence())
                .placeImageList(new ArrayList<>())
                .build();

        //이미지 세팅
        List<PlaceImage> placeImages = placeRequestDTO.getPlaceImagesURL().stream().map((imageUrl) -> {
            return PlaceImage.builder().imageURL(imageUrl).place(place).build();
        }).toList();

        placeImages.forEach(placeImage -> place.getPlaceImageList().add(placeImage));

        return place;
    }

}