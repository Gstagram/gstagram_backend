package com.gstagram.gstagram.city.repository;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.domain.QCity;
import com.gstagram.gstagram.region.domain.QRegion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

// interface에서 구현한 메소드를 여기서 query를 이용하여 작성한다
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CityCustomRepositoryImpl implements CityCustomRepository{

    private final JPAQueryFactory jqf;

    // 적절한 Q 클래스를 만들
    private final QCity city = new QCity("city");
    private final QRegion region = new QRegion("region");

    // querydsl 관련 예제
    // sql과 관련된 join, fetch join등등 거의 다 지원된다
    // DTO를 바로 전달하는 것도 가능
    @Override
    public List<City> findByRegionName(String regionName) {
        return jqf
                .select(city)
                .from(city)
                .where(city.region.regionName.like("%" + regionName + "%")).fetch();
    }

}
