package com.gstagram.gstagram.booklet.repository.bookletcaption;

import com.gstagram.gstagram.booklet.domain.Booklet;
import com.gstagram.gstagram.booklet.domain.BookletCaption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookletCatptionRepository extends JpaRepository<BookletCaption, Long> {

}

