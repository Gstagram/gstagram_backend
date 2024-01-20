package com.gstagram.gstagram.booklet.application;

import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.repository.booklet.BookletRepository;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.BookletException;
import com.gstagram.gstagram.common.exception.RegionException;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class BookletService {

    private final BookletRepository bookletRepository;
    private final RegionRepository regionRepository;


    /**
     * create
     *
     * @param regionId:region의 id값
     * @return region에 대한 booklet이 존재하더라도 새로운 booklet을 생성하지는 않음
     */
    @Transactional
    public Booklet saveBooklet(Long regionId) {

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RegionException(ResponseCode.REGION_NOT_FOUND));

        // 지역 당 하나의 booklet이 존재
        // 중복 확인 -> 저장 로직 수행x
        Optional<Booklet> bookletByRegion = bookletRepository.findBookletByRegionId(region.getId());
        return bookletByRegion.orElseGet(() -> bookletRepository.save(Booklet.builder().region(region).build()));
    }

    /**
     * read
     *
     * @param bookletId Booklet Entity의 id
     * @return 적절한 booklet을 return
     */
    // TODO: n + 1 문제 해결하려면 fetch join하면 되는데, 이 함수는 fetch join으로 구현하지는 않았다.
    public Booklet findBooklet(Long bookletId) {

        return bookletRepository.findById(bookletId)
                .orElseThrow(() -> new BookletException(ResponseCode.BOOKLET_NOT_FOUND));

    }


    public Booklet findBookletByRegionId(Long regionId) {

        return bookletRepository.findBookletByRegionId(regionId)
                .orElseThrow(() -> new BookletException(ResponseCode.REGION_NOT_FOUND));

    }

}
