package com.gstagram.gstagram.course.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CourseSearchDTO {

    List<String> cityNameList;
    List<String> regionNameList;

    String courseTitle;
}
