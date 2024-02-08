package com.gstagram.gstagram.city.application;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.RegionException;
import com.gstagram.gstagram.region.domain.Region;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CityService {

    private final CityRepository cityRepository;

    public City findByCityNameContaining(String cityName) {
        List<City> cities = cityRepository.findByCityNameContaining(cityName);
        if (cities.isEmpty()) {
            throw new RegionException(ResponseCode.REGION_NOT_FOUND);
        }
        return cities.get(0);
    }

    public List<City> findCitiesByRegion(Region region) {
        List<City> cities = cityRepository.findByRegion(region);
        if (cities.isEmpty()){
            throw new RegionException(ResponseCode.REGION_NOT_FOUND);
        }
        return cities;
    }

}
