package com.gstagram.gstagram.booklet.repository.booklet;

import com.gstagram.gstagram.booklet.domain.Booklet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookletRepository extends JpaRepository<Booklet, Long>, BookletCustomRepository {


    Optional<Booklet> findBookletByRegionId(Long regionId);

    //@Query("select booklet from Booklet booklet join fetch booklet.bookletCaptions where booklet.id = :id")
    //Optional<Booklet> findBookletByIdFetchJoin(@Param("id") Long id);


}
