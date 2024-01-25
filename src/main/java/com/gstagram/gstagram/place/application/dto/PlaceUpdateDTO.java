package com.gstagram.gstagram.place.application.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlaceUpdateDTO {
    @NotNull
    Long placeId;

    String placeName;

    String placeDescription;

    Double latitude;

    Double longitude;

    Integer sequence;

}
