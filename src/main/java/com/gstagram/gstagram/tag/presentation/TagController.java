package com.gstagram.gstagram.tag.presentation;

import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.tag.application.TagService;
import com.gstagram.gstagram.tag.dto.ResponseTagDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tag", description = "Tag API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "태그된 게시물 조회")
    @GetMapping("/{tagId}")
    public ApiResponse<List<Course>> getCourseListByTag(@PathVariable Long tagId) {
        return ApiResponse.success(ResponseCode.COURSE_ACCESS_SUCCESS, tagService.getCourseListByTag(tagId));
    }

    @Operation(summary = "태그 목록 조회")
    @GetMapping("/list")
    public ApiResponse<List<ResponseTagDto>> getAllTagList() {
        List<ResponseTagDto> tagList = tagService.getAllTagList().stream()
                .map(ResponseTagDto::from)
                .toList();
        return ApiResponse.success(ResponseCode.TAG_READ_SUCCESS, tagList);
    }
}
