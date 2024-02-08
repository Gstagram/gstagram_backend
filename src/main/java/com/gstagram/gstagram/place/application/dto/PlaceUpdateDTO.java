package com.gstagram.gstagram.place.application.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlaceUpdateDTO {


    String placeName;

    String placeDescription;

    Double latitude;

    Double longitude;

    Integer sequence;
    List<String> imageList;

}
