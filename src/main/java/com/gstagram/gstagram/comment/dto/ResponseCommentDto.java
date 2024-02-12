package com.gstagram.gstagram.comment.dto;

import com.gstagram.gstagram.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ResponseCommentDto {
    private Long commentId;
    private String content;
    private String userId;
    private String userName;
    private String profileImageS3Url;
    private LocalDateTime createdDate;

    public static ResponseCommentDto from(Comment comment) {
        return ResponseCommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getUuid())
                .userName(comment.getUser().getUsername())
                .profileImageS3Url(comment.getUser().getProfileImageS3Url())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}
