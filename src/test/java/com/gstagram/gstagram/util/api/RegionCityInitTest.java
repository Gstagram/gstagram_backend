package com.gstagram.gstagram.util.api;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * region, city에 대한 test
 * transactional은 rollback용 어노테이션
 * mock으로 할 때와 regionCity
 */

@Transactional
@SpringBootTest
@Slf4j
class RegionCityInitTest {

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    RegionCityInit regionCityInit;
    @Autowired
    private EntityManager entityManager;


    @Test
    @DisplayName("region 정보 받아오기")
    void region정보받아오기() throws IOException {
        //given

        // @PostConstruct -> 아래 함수를 의존관계 주입 후 호출 해준다
        // regionCityInit.RegionInit();
        //when
        List<Region> regions = regionRepository.findAll();

        //then
        // region(도 + 광역시)는 총 16개야 한다
        Assertions.assertEquals(regions.size(), 16);

    }


    // 재호출 하더라도, 값을 변경시키면 안된다
    @Test
    @DisplayName("region이 이미 db에 존재할 때, 값을 변경시키는지 확인하기")
    void regon정보중복check() throws IOException {
        //컨테이너 생성시점(postconstruct) + 수동 호출 ==> 총 2번 호출

        //given
        //regionCityInit.RegionInit();



        Region seoul1 = regionRepository.findByRegionName("서울특별시").get();
        //서울 사는놈 10명
        for (int i = 0; i < 10; i++) {
            seoul1.addUserCount();
        }
        // db에 동기화
        entityManager.flush();


        // when
        regionCityInit.RegionInit();


        //then
        //재호출 한다 해도 10명이 유지 돼야 한다
        Region seoul2 = regionRepository.findByRegionName("서울특별시").get();
        Assertions.assertEquals(seoul1, seoul2);
        Assertions.assertEquals(seoul2.getUserCount(), 10);

    }

    @Test
    @DisplayName("city 정보 받아오기")
    void city정보_받아오기() throws IOException {
        //given
        //역시나 postconstruct에서 만들어준다
        //regionCityInit.RegionInit();

        //when
        long count = cityRepository.count();

        //then
        assertThat(count).isGreaterThan(150);

    }

    @Test
    @DisplayName("city와 region이 잘 mapping 됐는지")
    void city_region매핑확인() throws IOException {

        //given
        regionCityInit.RegionInit();
        Region region = regionRepository.findByRegionName("경상북도").get();
        City city = cityRepository.findByCityName("경상북도 포항시").get();


        //when

        //then
        assertThat(region).isEqualTo(city.getRegion());


    }

}