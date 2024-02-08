package com.gstagram.gstagram.course.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "course의 이름, 설명을 수정하는 DTO")
public class CourseUpdateDTO {
    @NotNull
    Long courseId;
    String courseName;
    String description;
    String thumbNailUrl;


}
