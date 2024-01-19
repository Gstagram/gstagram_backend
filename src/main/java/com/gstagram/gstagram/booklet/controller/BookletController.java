package com.gstagram.gstagram.booklet.controller;

import com.gstagram.gstagram.booklet.application.BookletCaptionService;
import com.gstagram.gstagram.booklet.application.BookletService;
import com.gstagram.gstagram.booklet.application.dto.BookletCaptionRequestDTO;
import com.gstagram.gstagram.booklet.controller.dto.response.BookletResponseDTO;
import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.domain.BookletCaption;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.region.application.RegionService;
import com.gstagram.gstagram.region.domain.Region;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Booklet", description = "booklet 관련 api (read, update만 지원)")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/booklet")
public class BookletController {


    private final BookletCaptionService bookletCaptionService;
    private final BookletService bookletService;
    private final RegionService regionService;

    @Operation(summary = "지역 이름 -> 관련 booklet return", description = "해당 지역과 관련된 booklet들을 제공")
    @GetMapping("/{regionName}")
    public ApiResponse<List<BookletResponseDTO>> findBookletByRegionName(@PathVariable String regionName) {
        Region region = regionService.findByName(regionName);
        Booklet booklet = bookletService.findBookletByRegionId(region.getId());
        List<BookletResponseDTO> bookletResponseDTOS = BookletResponseDTO.BookletToDTO(booklet);
        return ApiResponse.success(bookletResponseDTOS, ResponseCode.BOOKLET_SEARCH_SUCCESS.getMessage());
    }


    @Operation(summary = "BookletCaptionId로 마음에 안드는 bookletcaption 수정하기", description = "" +
            "/bookletCaptionId(Long타입)/caption=넣고싶은데이터?imageUrl=넣고싶은데이터 형식")
    @PutMapping("/{bookletCaptionId}")
    public ApiResponse<BookletResponseDTO> updateBookletCaption(@PathVariable Long bookletCaptionId, @ModelAttribute BookletCaptionRequestDTO requestDTO) {

        BookletCaption bookletCaption = bookletCaptionService.findBookletCaption(bookletCaptionId);
        bookletCaptionService.changeBookletCaption(bookletCaption.getId(), requestDTO);
        BookletResponseDTO responseDTO = BookletResponseDTO.builder()
                .regionId(bookletCaption.getBooklet().getRegion().getId())
                .regionName(bookletCaption.getBooklet().getRegion().getRegionName())
                .bookletCaptionId(bookletCaption.getId())
                .caption(bookletCaption.getCaption())
                .imgURL(bookletCaption.getS3Url())
                .sequence(bookletCaption.getSequence()).build();
        return ApiResponse.success(responseDTO, ResponseCode.BOOKLET_SEARCH_SUCCESS.getMessage());
    }
}
