package com.gstagram.gstagram.course.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "course에 place추가하는 로직")
public class AddPlaceRequestDTO {
    Long courseID;
    PlaceRequestDTO placeRequestDTO;


}
