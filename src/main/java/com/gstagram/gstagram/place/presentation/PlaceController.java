package com.gstagram.gstagram.place.presentation;

import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.place.application.PlaceService;
import com.gstagram.gstagram.place.application.dto.PlaceUpdateDTO;
import com.gstagram.gstagram.place.domain.Place;
import com.gstagram.gstagram.place.presentation.dto.request.PlaceUpdateRequestDTO;
import com.gstagram.gstagram.place.presentation.dto.response.PlaceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "place", description = "place 관련 crud")
@Slf4j
@RestController
@RequestMapping("/api/course/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    @Operation(summary = "place 수정", description = "지정한 PK값으로 place를 수정")
    @PutMapping("/update/{placeId}")
    public ApiResponse<Boolean> updatePlace(@PathVariable("placeId") Long placeId, @Valid PlaceUpdateRequestDTO placeUpdateRequestDTO) {
        PlaceUpdateDTO serviceDTO = placeUpdateRequestDTO.toServiceDTO();
        placeService.changePlace(placeId, serviceDTO);
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, true);
    }


    @Operation(summary = "place 조회", description = "지정한 PK값으로 place를 조회")
    @GetMapping("/find/{placeId}")
    public ApiResponse<PlaceResponseDTO> findPlace(@PathVariable("placeId") Long placeId) {
        Place place = placeService.findPlaceById(placeId);
        PlaceResponseDTO from = PlaceResponseDTO.from(place);
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, from);
    }

    @Operation(summary = "place 삭제", description = "지정한 PK값으로 place를 삭제")
    @DeleteMapping("/delete/{placeId}")
    public ApiResponse<Boolean> deletePlace(@PathVariable("placeId") Long placeId) {
        placeService.deletePlaceById(placeId);
        return ApiResponse.success(ResponseCode.COURSE_UPDATE_SUCCESS, true);
    }

}
