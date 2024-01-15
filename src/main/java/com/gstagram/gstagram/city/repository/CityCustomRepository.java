package com.gstagram.gstagram.city.repository;

import com.gstagram.gstagram.city.domain.City;

import java.util.List;


// 구현할 기능의 메소드를 interface에서 구현한다
public interface CityCustomRepository {

    public List<City> findByRegionName(String regionName);
}
