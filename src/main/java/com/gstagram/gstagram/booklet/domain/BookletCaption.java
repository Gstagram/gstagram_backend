package com.gstagram.gstagram.booklet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "booklet_caption")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class BookletCaption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booklet_id")
    private Booklet booklet;

    @Column(name = "s3_url", nullable = false)
    private String s3Url;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "sequnce", nullable = false)
    private Integer sequence;


    public void changeImg(String s3Url) {
        this.s3Url = s3Url;
    }

    public void changeCaption(String caption) {
        this.caption = caption;
    }




}
