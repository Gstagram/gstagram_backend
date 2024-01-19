package com.gstagram.gstagram.util.init;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.booklet.application.BookletCaptionService;
import com.gstagram.gstagram.booklet.application.BookletService;
import com.gstagram.gstagram.booklet.application.dto.BookletCaptionRequestDTO;
import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.repository.bookletcaption.BookletCatptionRepository;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookletInit {

    private final BookletService bookletService;
    private final BookletCaptionService bookletCaptionService;
    private final BookletCatptionRepository bookletCatptionRepository;
    private final RegionRepository regionRepository;


    /**
     * booklet 설정하기
     * */
    @EventListener(ApplicationReadyEvent.class)
    public void bookletInit() {
        if (bookletCatptionRepository.count() > 0 ){
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("booklet.json");

        List<RegionDTO> regionDTOList = new ArrayList<>();
        try {
            regionDTOList = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<RegionDTO>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        regionDTOList.forEach((regionDTO -> {
            log.info(regionDTO.regionName);
            Region region = regionRepository.findByRegionName(regionDTO.regionName).get();
            Booklet booklet = bookletService.saveBooklet(region.getId());
            regionDTO.getBookletCaptions().forEach(bookletCaptionRequestDTO -> bookletCaptionService.saveBookletCaption(booklet.getId(), bookletCaptionRequestDTO));


        }));

    }

    @Data
    public static class RegionDTO {
        private String regionName;
        private List<BookletCaptionRequestDTO> bookletCaptions;

    }

}
