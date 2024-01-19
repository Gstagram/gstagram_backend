package com.gstagram.gstagram.booklet.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Controller에서 service 코드를 쓰기 위한 DTO
 * sequence로 String을 입력하는 것을 방지하기 위해서 별도의 dto를 생성
 * api 스펙과는 별도의 내용
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookletCaptionRequestDTO {
    /***
     * caption: BookletCaption에 저장할 caption내용
     */
    private String caption;
    /***
     * imageUrl: BookletCaption에 저장할 이미지 url
     */
    private String imageUrl;


}
