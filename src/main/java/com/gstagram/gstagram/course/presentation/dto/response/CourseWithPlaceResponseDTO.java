package com.gstagram.gstagram.course.presentation.dto.response;

import com.gstagram.gstagram.place.presentation.dto.response.PlaceResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "course + place 같이 보내기, 아래 CourseResponseDTO, PlaceResponseDTO 스키마를 참고")
public class CourseWithPlaceResponseDTO {
    @Schema(description = "course 정보")
    CourseResponseDTO courseResponseDTO;
    @Schema(description = "course 하위의 place들 정보")
    List<PlaceResponseDTO> placeResponseDTOList;
}
