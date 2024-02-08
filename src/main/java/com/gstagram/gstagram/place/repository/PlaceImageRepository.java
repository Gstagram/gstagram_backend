package com.gstagram.gstagram.place.repository;

import com.gstagram.gstagram.place.domain.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
