package com.gstagram.gstagram.course.application.dto.response;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.place.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
/**
 * service -> controller 계층에서 course와 place들을 전달하는 dto
 * API스펙과는 무관
 */
public class CourseWithPlaceDTO {

    Course course;
    List<Place> placeList;


}
