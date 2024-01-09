package com.gstagram.gstagram.booklet.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "booklet_caption")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class BookletCaption {
    @Id
    @ManyToOne
    @JoinColumn(name = "booklet_id")
    private Booklet booklet;

    @Column(name = "s3_url", nullable = false)
    private String s3Url;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "sequence", nullable = false)
    private int sequence;

}
