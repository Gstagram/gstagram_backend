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
    @Schema(description = "course PK값", example = "1")
    private Long courseId;
    @Schema(description = "course 제목",  example = "서울의 숨겨진 보석 탐방")
    private String courseName;
    @Schema(description = "course 설명",  example = "서울의 숨겨진 역사적 장소들을 탐방하는 코스입니다.")
    private String description;
    @Schema(description = "작성자 PK값",  example = "abcd1234-ef56-7890-gh12-ijkl3456mnop")
    private String writerId;
    @Schema(description = "소속 region 이름", example = "경상북도")
    private String regionName;
    @Schema(description = "소속 city 이름", example = "포항시")
    private String cityName;
    @Schema(description = "썸네일 URL", example = "https://yeeeeum.s3.ap-northeast-2.amazonaws.com/%EC%84%9C%EC%9A%B83.jpg")
    private String thumbNailUrl;
    @Schema(description = "course 작성시간", example = "2023-01-01T12:00:00")
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
