package com.gstagram.gstagram.course.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.course.domain.QCourse;
import com.gstagram.gstagram.course.repository.dto.CourseSearchCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseQueryRepositoryImpl implements CourseQueryRepository {


    private final JPAQueryFactory queryFactory;
    private final QCourse course = new QCourse("course");


    @Override
    public Page<Course> searchCourseBySearchConditionOrderByDate(CourseSearchCond courseSearchCond, Pageable pageable) {

        //where절 동적으로 구성하기 위한 영역
        BooleanBuilder builder = new BooleanBuilder();
        if (courseSearchCond.getCityList() != null) {
            courseSearchCond.getCityList().forEach(city -> builder.or(course.city.eq(city)));
        }
        if (courseSearchCond.getRegionList() != null) {
            courseSearchCond.getRegionList().forEach(region -> builder.or(course.region.eq(region)));
        }

        if (StringUtils.hasText(courseSearchCond.getCourseTitle())) {
            String titlePattern = "%" + courseSearchCond.getCourseTitle() + "%";
            builder.and(course.courseName.like(titlePattern)); // LIKE 연산자를 사용하여 제목 검색
        }


        OrderSpecifier<LocalDateTime> orderByCreatedTime = new OrderSpecifier<>(Order.DESC, course.createdTime); // 내림차순 정렬

        List<Course> courses = queryFactory
                .selectFrom(course)
                .where(builder)
                .orderBy(orderByCreatedTime)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        return new PageImpl<>(courses, pageable, courses.size());
    }

}

