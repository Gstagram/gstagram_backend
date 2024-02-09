package com.gstagram.gstagram.course.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "course 게시물 검색 조건, city, region은 or연산으로 할듯 ==> 의견있으면 정중하게 DM 보내3")
public class CourseSearchDTO {

    @Schema(description = "검색할 city이름들")
    List<String> cityNameList;
    @Schema(description = "검색할 region 이름들")
    List<String> regionNameList;
    @Schema(description = "검색할 제목")
    String courseTitle;
}
