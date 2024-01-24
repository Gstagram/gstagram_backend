package com.gstagram.gstagram.place.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlaceUpdateDTO {

    Long placeId;

    String placeName;

    String placeDescription;

    Double latitude;

    Double longitude;

    Integer sequence;

}
