package com.gstagram.gstagram.region.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.BaseException;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public Region findByName(String regionName) {
        return regionRepository.findByRegionName(regionName)
                .orElseThrow(() -> new BaseException(ResponseCode.REGION_NOT_FOUND));


    }


}
