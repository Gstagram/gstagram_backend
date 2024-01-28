package com.gstagram.gstagram.booklet.application;

import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.repository.booklet.BookletRepository;
import com.gstagram.gstagram.booklet.repository.bookletcaption.BookletCatptionRepository;
import com.gstagram.gstagram.common.exception.BaseException;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("default") //booklet init 비활성화시키기 위해서
class BookletServiceTest {

    @Autowired
    BookletService bookletService;
    @Autowired
    BookletRepository bookletRepository;
    @Autowired
    BookletCatptionRepository bookletCatptionRepository;
    @Autowired
    RegionRepository regionRepository;

    @Test
    @DisplayName("create: booklet 저장 확인")
    void booklet_save() {

        // given
        Region region = regionRepository.findByRegionName("경상북도").get();

        //when
        Booklet booklet = bookletService.saveBooklet(region.getId());

        //then
        Assertions.assertThat(booklet).isIn(bookletRepository.findAll());

    }

    @Test
    @DisplayName("create: booklet 저장 확인")
    void booklet_save_error() {

        // given


        //when + then
        assertThrows(BaseException.class, () -> bookletService.saveBooklet(1234L));

    }

    @Test
    @DisplayName("create: booklet 같은 지역에 중복 저장")
    void booklet_중복저장() {

        // given -> 이미 한번 저장함
        Region region = regionRepository.findByRegionName("경상북도").get();
        Booklet booklet = bookletService.saveBooklet(region.getId());

        //when
        Booklet booklet1 = bookletService.saveBooklet(region.getId());

        // then
        // 여러 번 저장을 시도하려 해도 한번만 저장해야한다
        // 지역당 booklet은 한개만..
        Assertions.assertThat(booklet).isIn(bookletRepository.findAll());


    }

    @Test
    @DisplayName("read: booklet id로 조회")
    void read_booklet_test() {

        //given
        Region region = regionRepository.findByRegionName("경상북도").get();
        Booklet booklet = bookletService.saveBooklet(region.getId());

        //when
        Booklet booklet1 = bookletService.findBooklet(booklet.getId());

        //then
        Assertions.assertThat(booklet1).isEqualTo(booklet);
    }

    @Test
    @DisplayName("read: 존재하지 않는 booklet 케이스")
    void name() {

        //given

        //when

        //then
        assertThrows(BaseException.class, () -> bookletService.findBooklet(1234L));

    }


}
