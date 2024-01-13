package com.gstagram.gstagram.util.api;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.gstagram.gstagram.util.api.UrlJsonHandler.readJsonFromUrl;

/**
 * region, city에 대한 정보를 받아오는 class
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegionCityInit {


    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;



}
