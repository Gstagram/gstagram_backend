package com.gstagram.gstagram.comment.presentation;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.comment.application.CommentService;
import com.gstagram.gstagram.comment.dto.CreateCommentDto;
import com.gstagram.gstagram.comment.dto.ResponseCommentDto;
import com.gstagram.gstagram.comment.dto.UpdateCommentDto;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글", description = "댓글 관련 API")
@Slf4j
@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    // 댓글 등록
    @Operation(summary = "댓글 등록", description = "댓글을 등록합니다.")
    @PostMapping()
    public ApiResponse<Void> comment(@RequestHeader String accessToken, CreateCommentDto createCommentDto) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        commentService.comment(userId, createCommentDto);
        return ApiResponse.success(ResponseCode.COMMENT_CREATED_SUCCESS, null);
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId, @RequestHeader String accessToken) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        commentService.deleteComment(userId, commentId);
        return ApiResponse.success(ResponseCode.COMMENT_DELETED_SUCCESS, null);
    }
    // 댓글 수정
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping()
    public ApiResponse<Void> updateComment(@RequestHeader String accessToken, @RequestBody UpdateCommentDto updateCommentDto) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        commentService.updateComment(userId, updateCommentDto);
        return ApiResponse.success(ResponseCode.COMMENT_UPDATED_SUCCESS, null);
    }
    // 댓글 수 조회
    @Operation(summary = "댓글 수 조회", description = "댓글 수를 조회합니다.")
    @GetMapping("/count/{courseId}")
    public ApiResponse<Integer> getCommentCount(@PathVariable Long courseId) {
        return ApiResponse.success(ResponseCode.COMMENT_READ_SUCCESS, commentService.getCommentCount(courseId));
    }

    // 댓글 조회
    @Operation(summary = "댓글 조회", description = "댓글을 조회합니다.")
    @GetMapping("/{courseId}")
    public ApiResponse<List<ResponseCommentDto>> getCommentList(@PathVariable Long courseId) {
        List<ResponseCommentDto> commentList = commentService.getCommentList(courseId).stream().map(ResponseCommentDto::from).toList();
        return ApiResponse.success(ResponseCode.COMMENT_READ_SUCCESS, commentList);
    }

    // 유저별 댓글 조회
    @Operation(summary = "유저별 댓글 조회", description = "유저별 댓글을 조회합니다.")
    @GetMapping("/user")
    public ApiResponse<List<ResponseCommentDto>> getCommentListByUserId(@RequestHeader String accessToken) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        List<ResponseCommentDto> commentList = commentService.getCommentListByUserId(userId).stream().map(ResponseCommentDto::from).toList();
        return ApiResponse.success(ResponseCode.COMMENT_READ_SUCCESS, commentList);
    }
}
