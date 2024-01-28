package com.gstagram.gstagram.course.presentation.dto.request;

import com.gstagram.gstagram.course.application.dto.request.CourseSearchDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(description = "course 게시물 검색 DTO")
public class CourseFindCond {
    @Schema(description = "city 이름을 담을 리스트")
    List<String> cityNameList;
    @Schema(description = "region 이름을 담을 리스트")
    List<String> regionNameList;
    @Size(min = 2, max = 500)
    @Schema(description = "course 게시물의 제목을 검색하기 위한 string값")
    String courseTitle;
    @Schema(description = "페이징을 위한 pageNumber")
    Integer pageNumber;
    @Schema(description = "페이징크기")
    Integer pageSize;
    public CourseSearchDTO toServiceDTO(){
        return CourseSearchDTO.builder()
                .courseTitle(courseTitle)
                .cityNameList(cityNameList)
                .regionNameList(regionNameList)
                .build();
    }
}
