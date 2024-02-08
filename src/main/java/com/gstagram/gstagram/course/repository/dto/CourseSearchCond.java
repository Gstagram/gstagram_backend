package com.gstagram.gstagram.course.repository.dto;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.region.domain.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@Builder
public class CourseSearchCond {

    List<City> cityList;
    List<Region> regionList;

    String courseTitle;


    /**
     * todo: 나중에 hashTag있으면 hashtag에 대한 내용도 추가해야 한다
     * */
}
