package com.gstagram.gstagram.comment.application;

import com.gstagram.gstagram.comment.domain.Comment;
import com.gstagram.gstagram.comment.dto.CreateCommentDto;
import com.gstagram.gstagram.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    void comment(String userId, CreateCommentDto createCommentDto);

    void deleteComment(String userId, Long commentId);
    void updateComment(String userId, UpdateCommentDto updateCommentDto);
    int getCommentCount(Long courseId);
    List<Comment> getCommentList(Long courseId);
    List<Comment> getCommentListByUserId(String userId);
}
