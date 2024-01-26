package com.gstagram.gstagram.place.domain;

import com.gstagram.gstagram.course.domain.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceImage> placeImageList = new ArrayList<>();

    public void changePlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void changeLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void changeSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public PlaceImage addImage(String image){
        if (this.getPlaceImageList() == null){
            this.placeImageList = new ArrayList<>();
        }
        PlaceImage placeImage = PlaceImage.builder().imageURL(image).place(this).build();
        placeImageList.add(placeImage);
        return placeImage;
    }

    public List<String> getImageURLString(){
        if (this.getPlaceImageList() == null){
            return new ArrayList<>();
        }
        return placeImageList.stream().map(PlaceImage::getImageURL).toList();
    }

}
