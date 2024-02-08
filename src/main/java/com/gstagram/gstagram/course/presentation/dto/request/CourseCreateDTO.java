package com.gstagram.gstagram.course.presentation.dto.request;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "course 게시물 요청 DTO")
public class CourseCreateDTO {

    @Schema(description = "region 이름 ex) 경상북도, 서울특별시...")
    @NotBlank
    String courseName;

    @Schema(description = "region 이름 ex) 경상북도, 서울특별시...")
    @NotBlank
    String regionName;

    @Schema(description = "city 이름, 당연하지만 region가 맞지 않는 city는 fail " +
            "ex: 전라북도 포항시")
    @NotBlank
    String cityName;

    @Schema(description = "course에 대한 설명")
    @NotBlank
    @Size(min = 2, max = 500)
    String courseDescription;

    @Schema(description = "course 맨 처음에 보일 썸네일 이미지 URL")
    @NotBlank
    @Pattern(regexp = "http(s)?://.+", message = "적절한 url 패턴이 필요합니다") // 예시: URL 패턴 검증
    String thumbnailURL;

    List<PlaceRequestDTO> placeRequestDTOList;

    public static Course toEntity(CourseCreateDTO createDTO, Region region, City city, User user) {
        return Course.builder()
                .courseName(createDTO.getCourseName())
                .region(region)
                .city(city)
                .user(user)
                .description(createDTO.courseDescription)
                .thumbNailUrl(createDTO.thumbnailURL)
                .build();

    }

}


