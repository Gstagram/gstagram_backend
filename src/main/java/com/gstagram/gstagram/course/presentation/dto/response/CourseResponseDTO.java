package com.gstagram.gstagram.course.presentation.dto.response;

import com.gstagram.gstagram.course.application.dto.request.CourseSearchDTO;
import com.gstagram.gstagram.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "course 응답 DTO")
public class CourseResponseDTO {
    @Schema(description = "course PK값")
    private Long courseId;
    @Schema(description = "course 제목")
    private String courseName;
    @Schema(description = "course 설명")
    private String description;
    @Schema(description = "작성자 PK값")
    private String writerId;
    @Schema(description = "소속 region 이름")
    private String regionName;
    @Schema(description = "소속 city 이름")
    private String cityName;
    @Schema(description = "썸네일 URL")
    private String thumbNailUrl;
    @Schema(description = "course 작성시간")
    private LocalDateTime createdTime;

    public static CourseResponseDTO from(Course course) {

        return CourseResponseDTO.builder().courseId(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .writerId(course.getUser().getUuid())
                .regionName(course.getRegion().getRegionName())
                .cityName(course.getCity().getCityName())
                .thumbNailUrl(course.getThumbNailUrl())
                .createdTime(course.getCreatedTime())
                .build();
    }
}
