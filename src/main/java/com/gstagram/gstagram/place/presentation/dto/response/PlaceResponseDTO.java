package com.gstagram.gstagram.place.presentation.dto.response;

import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.domain.PlaceImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "place 정보 요청 DTO")
public class PlaceResponseDTO {
    @Schema(description = "place pk값")
    private Long placeId;
    @Schema(description = "사용자가 지정한 place 이름")
    private String placeName;
    @Schema(description = "place 설명")
    private String description;
    @Schema(description = "위도")
    private Double latitude;
    @Schema(description = "경도")
    private Double longitude;
    @Schema(description = "순서")
    private Integer sequence;
    @Schema(description = "place와 관련있는 course의 pk값")
    private Long relatedCourseId;
    @Schema(description = "place 작성글 안에 삽입한 이미지")
    private List<String> placeImageList;


    public static PlaceResponseDTO from(Place place) {


         return PlaceResponseDTO.builder().placeId(place.getId())
                .placeName(place.getPlaceName())
                .description(place.getDescription())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .relatedCourseId(place.getCourse().getId())
                .placeImageList(place.getImageURLString()).build();
    }
}
