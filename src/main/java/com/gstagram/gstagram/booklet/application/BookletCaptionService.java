package com.gstagram.gstagram.booklet.application;

import com.gstagram.gstagram.booklet.application.dto.BookletCaptionRequestDTO;
import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.domain.BookletCaption;
import com.gstagram.gstagram.booklet.repository.booklet.BookletRepository;
import com.gstagram.gstagram.booklet.repository.bookletcaption.BookletCatptionRepository;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.BookletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookletCaptionService {

    private final BookletRepository bookletRepository;
    private final BookletCatptionRepository bookletCatptionRepository;


    /**
     * create
     *
     * @param bookletId 저장할 booklet
     * @param request   booklet caption에 저장할 양식 (api 스펙과는 무관)
     * @return 저장한 booklet caption을 return한다
     */
    //dto 만든 용도 sequence 에러 (string, string 이렇게 했을 때 혹시나 잘못입력하면 어캄..)
    //해당 region의 새로운 booklet 만들기
    @Transactional
    public BookletCaption saveBookletCaption(Long bookletId, BookletCaptionRequestDTO request) {

        Booklet booklet = bookletRepository.findById(bookletId).orElseThrow(() -> new BookletException(ResponseCode.BOOKLET_NOT_FOUND));

        BookletCaption bookletCaption = BookletCaption.builder()
                .booklet(booklet)
                .caption(request.getCaption())
                .s3Url(request.getImageUrl())
                .sequence(booklet.getBookletCaptions().size() + 1)
                .build();


        booklet.getBookletCaptions().add(bookletCaption);
        bookletCatptionRepository.save(bookletCaption);


        return bookletCaption;
    }


    /**
     * read
     *
     * @param bookletCaptionId : 조회할 booklet Caption
     * @return repository에서 찾은 booklet Caption
     */
    public BookletCaption findBookletCaption(Long bookletCaptionId) {

        return bookletCatptionRepository.findById(bookletCaptionId).orElseThrow(() -> new BookletException(ResponseCode.BOOKLET_NOT_FOUND));
    }


    /**
     * update
     *
     * @param bookletCaptionId 수정할 booklet Caption
     * @param request          수정할 booklet Caption내용
     * @return update한 booklet Caption
     */
    @Transactional
    public BookletCaption changeBookletCaption(Long bookletCaptionId, BookletCaptionRequestDTO request) {

        BookletCaption bookletCaption = bookletCatptionRepository.findById(bookletCaptionId)
                .orElseThrow(() -> new BookletException(ResponseCode.BOOKLET_NOT_FOUND));
        // null + empty인 경우를 check해준다 --> spring에서 제공해주는 기능
        if (StringUtils.hasText(request.getCaption())) {
            bookletCaption.changeCaption(request.getCaption());
        }

        if (StringUtils.hasText(request.getImageUrl())) {
            bookletCaption.changeImg(request.getImageUrl());
        }
        return bookletCaption;
    }


    /**
     * delete
     *
     * @param bookletCaptionId 삭제할 booklet Caption
     */
    @Transactional
    public void deleteBookletCation(Long bookletCaptionId) {
        BookletCaption bookletCaption = bookletCatptionRepository.findById(bookletCaptionId)
                .orElseThrow(() -> new BookletException(ResponseCode.BOOKLET_NOT_FOUND));

        Booklet booklet = bookletCaption.getBooklet();

        // jpa에서는 하나의 transaction에서는 객체의 동일성을 보장 해준다
        booklet.getBookletCaptions().remove(bookletCaption);
        bookletCatptionRepository.delete(bookletCaption);

    }


}
