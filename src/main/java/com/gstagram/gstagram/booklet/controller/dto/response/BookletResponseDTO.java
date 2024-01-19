package com.gstagram.gstagram.booklet.controller.dto.response;

import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.domain.BookletCaption;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookletResponseDTO {
    Long regionId;
    String regionName;
    Long bookletCaptionId;
    String caption;
    String imgURL;
    Integer sequence;

    public static List<BookletResponseDTO> BookletToDTO(Booklet booklet) {
        List<BookletCaption> bookletCaptions = booklet.getBookletCaptions();
        List<BookletResponseDTO> result = new ArrayList<>();
        return bookletCaptions.stream().map(bookletCaption -> {
            return BookletResponseDTO.builder()
                    .regionId(booklet.getRegion().getId())
                    .regionName(booklet.getRegion().getRegionName())
                    .bookletCaptionId(bookletCaption.getId())
                    .caption(bookletCaption.getCaption())
                    .imgURL(bookletCaption.getS3Url())
                    .sequence(bookletCaption.getSequence()).build();
        }).toList();
    }


}
