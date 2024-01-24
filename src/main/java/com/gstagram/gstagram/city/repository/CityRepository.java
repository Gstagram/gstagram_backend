package com.gstagram.gstagram.city.repository;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String name);


    //@Query("SELECT c FROM City c WHERE c.name LIKE %:cityName%") -> sql 인젝션에 취약하다고 함
    List<City> findByCityNameContaining(String name);

    List<City> findByRegion(Region region);

}
