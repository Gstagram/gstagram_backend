package com.gstagram.gstagram.region.application;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.BaseException;
import com.gstagram.gstagram.common.exception.RegionException;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public Region findByName(String regionName) {
        return regionRepository.findByRegionName(regionName)
                .orElseThrow(() -> new BaseException(ResponseCode.REGION_NOT_FOUND));


    }

    public Region findByNameContaining(String regionName) {
        List<Region> regions = regionRepository.findRegionByRegionNameContaining(regionName);

        if (regions.isEmpty()) {
            throw new RegionException(ResponseCode.REGION_NOT_FOUND);
        }

        return regions.get(0);
    }


}
