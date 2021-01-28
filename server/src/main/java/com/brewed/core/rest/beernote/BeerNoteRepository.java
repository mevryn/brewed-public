package com.brewed.core.rest.beernote;

import com.brewed.core.model.beer.BeerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BeerNoteRepository extends JpaRepository<BeerNote, UUID> {
    @Query(value = "SELECT note FROM BeerNote note where note.note = :note")
    Optional<BeerNote> findByNote(@Param(value = "note") String note);

}
