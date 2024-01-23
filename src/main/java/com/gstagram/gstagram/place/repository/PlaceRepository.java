package com.gstagram.gstagram.place.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>{




}
