package com.gstagram.gstagram.booklet.repository.booklet;


import com.gstagram.gstagram.booklet.domain.QBooklet;
import com.gstagram.gstagram.booklet.domain.QBookletCaption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookletCustomRepositoryImpl implements BookletCustomRepository {


    private final JPAQueryFactory jqf;
    private QBooklet booklet = new QBooklet("booklet");
    private QBookletCaption bookletCaption = new QBookletCaption("booklet_caption");


}
