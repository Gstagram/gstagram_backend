package com.gstagram.gstagram.place.repository;

import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {


    List<Place> findAllByCourse(Course course);

    void deleteAllByCourse(Course course);

}
