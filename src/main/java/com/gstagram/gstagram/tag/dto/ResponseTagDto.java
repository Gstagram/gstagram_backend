package com.gstagram.gstagram.tag.dto;

import com.gstagram.gstagram.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseTagDto {
    private Long tagId;
    private String tagName;

    public static ResponseTagDto from(Tag tag) {
        return ResponseTagDto.builder()
                .tagId(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
