package com.gstagram.gstagram.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateCommentDto {
    private Long commentId;
    private String content;
}
