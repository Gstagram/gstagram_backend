package com.gstagram.gstagram.booklet.domain;

import com.gstagram.gstagram.region.domain.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Booklet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "region_id")
    private Region region;


    // cascasde All  + orphanRemoval
    // 1) 생명주기 동일
    // 2) booklet_caption은 only booklet에게만 관리
    // 3) booklet과  booklet caption이 많은 경우에 같이  쓰일 것 같다 -> 따로 db찾기보다는 사용하기 편할듯
    @OneToMany(mappedBy = "booklet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookletCaption> bookletCaptions = new ArrayList<>();
}

