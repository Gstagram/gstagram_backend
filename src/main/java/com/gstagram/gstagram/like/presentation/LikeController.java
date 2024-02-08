package com.gstagram.gstagram.like.presentation;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.like.application.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 관련 API")
@Slf4j
@RequestMapping("/api/like")
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final JwtTokenProvider jwtTokenProvider;

    // 좋아요
    @Operation(summary = "[좋아요] 좋아요", description = "좋아요를 누릅니다.")
    @PostMapping("/{courseId}")
    public ApiResponse<Void> like(@RequestHeader String accessToken, @PathVariable Long courseId) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        likeService.like(userId, courseId);
        return ApiResponse.success(ResponseCode.LIKE_CREATED_SUCCESS, null);
    }

    // 좋아요 취소
    @Operation(summary = "[좋아요] 좋아요 취소", description = "좋아요를 취소합니다.")
    @DeleteMapping("/{courseId}")
    public ApiResponse<Void> unlike(@RequestHeader String accessToken, @PathVariable Long courseId) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        likeService.unlike(userId, courseId);
        return ApiResponse.success(ResponseCode.LIKE_DELETED_SUCCESS, null);
    }

    // 좋아요한 유저 조회
    @Operation(summary = "[좋아요] 좋아요한 유저 조회", description = "좋아요한 유저를 조회합니다.")
    @GetMapping("/{courseId}/user")
    public ApiResponse<Boolean> isLiked(@RequestHeader String accessToken, @PathVariable Long courseId) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        return ApiResponse.success(ResponseCode.LIKE_READ_SUCCESS, likeService.isLiked(userId, courseId));
    }

}
