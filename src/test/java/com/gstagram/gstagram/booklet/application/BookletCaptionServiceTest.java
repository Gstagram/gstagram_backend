package com.gstagram.gstagram.booklet.application;


import com.gstagram.gstagram.booklet.application.dto.BookletCaptionRequestDTO;
import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.domain.BookletCaption;
import com.gstagram.gstagram.booklet.repository.bookletcaption.BookletCatptionRepository;
import com.gstagram.gstagram.common.exception.BaseException;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // junit5 에서 beforeAll을 non-static하게 하기 위해서..
@Slf4j
@ActiveProfiles("default") //booklet init 비활성화시키기 위해서
public class BookletCaptionServiceTest {


    @Autowired
    RegionRepository regionRepository;
    @Autowired
    BookletService bookletService;
    @Autowired
    BookletCaptionService bookletCaptionService;
    @Autowired
    BookletCatptionRepository bookletCatptionRepository;


    Region region;
    //얘는 booklet id 때매..
    Booklet booklet;

    @BeforeAll
    public void beforeAll() {
        region = regionRepository.findByRegionName("경상북도").get();
        booklet = bookletService.saveBooklet(region.getId());

    }


    @Test
    @DisplayName("create: booklet caption생성")
    void booklet_caption저장() {
        // given
        BookletCaptionRequestDTO requestDTO = BookletCaptionRequestDTO.builder().imageUrl("image123").caption("CaptionExample").build();

        //when
        BookletCaption bookletCaption = bookletCaptionService.saveBookletCaption(booklet.getId(), requestDTO);

        //then
        Assertions.assertThat(bookletCaption).isIn(bookletCatptionRepository.findAll());
        Assertions.assertThat(bookletCaption).isIn(bookletCaption.getBooklet().getBookletCaptions());


    }

    @Test
    @DisplayName("create: booklet caption 생성 - booklet이 없는 경우")
    void booklet_caption이_없는경우() {
        // given
        BookletCaptionRequestDTO requestDTO = BookletCaptionRequestDTO.builder().imageUrl("image123").caption("CaptionExample").build();

        //when

        //then
        assertThrows(BaseException.class, () -> bookletCaptionService.saveBookletCaption(12345L, requestDTO));

    }

    @Test
    @DisplayName("update: booklet caption수정")
    void booklet_caption수정() {

        // given
        BookletCaptionRequestDTO requestDTO = BookletCaptionRequestDTO.builder().imageUrl("image123").caption("CaptionExample").build();
        BookletCaption bookletCaption = bookletCaptionService.saveBookletCaption(booklet.getId(), requestDTO);

        //when
        //booklet 수정하기
        BookletCaptionRequestDTO requestDTO1 = BookletCaptionRequestDTO.builder().imageUrl("image1234").caption("CaptionExample").build();
        bookletCaptionService.changeBookletCaption(bookletCaption.getId(), requestDTO1);

        //then
        Assertions.assertThat(bookletCaption.getCaption()).isEqualTo(requestDTO1.getCaption());
        Assertions.assertThat(bookletCaption.getS3Url()).isEqualTo(requestDTO1.getImageUrl());
    }

    @Test
    @DisplayName("read: booklet caption 조회")
    void booklet_caption조회() {
        // given

        BookletCaptionRequestDTO requestDTO = BookletCaptionRequestDTO.builder().imageUrl("image123").caption("CaptionExample").build();
        BookletCaption bookletCaption = bookletCaptionService.saveBookletCaption(booklet.getId(), requestDTO);
        Booklet booklet1 = bookletService.findBooklet(booklet.getId());

        //when
        BookletCaption bookletCaption1 = bookletCaptionService.findBookletCaption(bookletCaption.getId());

        //then
        Assertions.assertThat(booklet1.getBookletCaptions().get(0)).isEqualTo(bookletCaption1);
        Assertions.assertThat(bookletCaption).isEqualTo(bookletCaption1);
    }

    @Test
    @DisplayName("read: booklet caption 조회")
    void booklet_caption삭제() {
        // given

        BookletCaptionRequestDTO requestDTO = BookletCaptionRequestDTO.builder().imageUrl("image123").caption("CaptionExample").build();
        BookletCaption bookletCaption = bookletCaptionService.saveBookletCaption(booklet.getId(), requestDTO);
        Booklet booklet1 = bookletService.findBooklet(booklet.getId());

        //when
        bookletCaptionService.deleteBookletCation(bookletCaption.getId());

        //then
        Assertions.assertThat(booklet1.getBookletCaptions().size()).isEqualTo(0);
        assertThrows(BaseException.class, () -> bookletCaptionService.findBookletCaption(bookletCaption.getId()));
    }

}
