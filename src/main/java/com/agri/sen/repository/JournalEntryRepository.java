package com.agri.sen.repository;

import com.agri.sen.entity.JournalEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long>,
        QuerydslPredicateExecutor<JournalEntry> {


    // Option 1 : Query method basée sur les noms de champs
    Double findSumCoutByParcelleIdAndDateActiviteBetween(Long parcelleId, LocalDateTime startDate, LocalDateTime endDate);

    // Option 2 : Avec @Query JPQL explicite
    @Query("SELECT SUM(j.cout) FROM JournalEntry j WHERE j.parcelleId = :parcelleId AND j.dateActivite BETWEEN :startDate AND :endDate")
    Double getTotalCostByParcelleIdAndDateActiviteBetween(
            @Param("parcelleId") Long parcelleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

