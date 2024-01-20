package com.gstagram.gstagram.util.init;

import com.gstagram.gstagram.booklet.repository.booklet.BookletRepository;
import com.gstagram.gstagram.booklet.repository.bookletcaption.BookletCatptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")// booklet init을 check하기 위해서
class RegionCityInitTest {

    @Autowired
    BookletCatptionRepository bookletCatptionRepository;
    @Autowired
    BookletRepository bookletRepository;

    @Test
    @DisplayName("region별 booklet caption이 활성화")
    void name() {
        Assertions.assertThat(bookletCatptionRepository.count()).isGreaterThan(30);
    }
}