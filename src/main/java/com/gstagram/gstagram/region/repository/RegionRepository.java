package com.gstagram.gstagram.region.repository;

import com.gstagram.gstagram.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

    Optional<Region> findByRegionName(String name);



}