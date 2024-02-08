package com.gstagram.gstagram.place.presentation.dto.request;

import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
import com.gstagram.gstagram.util.validation.ValidUrl;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//controller에서 api로 받는 DTO와 controller -> service의 DTO을 구분짓기 위함
// controller -> service의 DTO에서 validation을 책임지지 않도록 별도 class로 분리
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class PlaceUpdateRequestDTO {

    @Size(min = 2, max = 50)
    String placeName;

    @Size(min = 10, max = 5000)
    String placeDescription;

    Double latitude;

    Double longitude;

    Integer sequence;

    @ValidUrl //list 형식일 때, url 형식인지 check
    List<String> imageList;

    public PlaceUpdateDTO toServiceDTO() {
        return PlaceUpdateDTO.builder()
                .placeName(this.placeName)
                .placeDescription(placeDescription)
                .latitude(latitude)
                .longitude(longitude)
                .sequence(sequence)
                .imageList(imageList).build();
    }
}
