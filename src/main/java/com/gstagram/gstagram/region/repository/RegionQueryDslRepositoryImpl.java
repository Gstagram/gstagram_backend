package com.gstagram.gstagram.region.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionQueryDslRepositoryImpl implements RegionQueryDslRepository {

    private final JPAQueryFactory jqf;





}
