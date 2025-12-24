package com.agri.sen.repository;

import com.agri.sen.entity.Parcelle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelleRepository extends JpaRepository<Parcelle, Long>, QuerydslPredicateExecutor<Parcelle> {

    // Utiliser @Query pour calculer la somme des superficies par agriculteurId
    @Query("SELECT SUM(p.superficie) FROM Parcelle p WHERE p.agriculteurId = :agriculteurId")
    Double getTotalSuperficieByAgriculteur(@Param("agriculteurId") Long agriculteurId);

    List<Parcelle> findByAgriculteurId(Long agriculteurId);

}
