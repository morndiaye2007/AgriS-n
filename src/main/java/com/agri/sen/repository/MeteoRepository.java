// MeteoRepository.java
package com.agri.sen.repository;

import com.agri.sen.entity.Meteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeteoRepository extends JpaRepository<Meteo, Long>, QuerydslPredicateExecutor<Meteo> {
    List<Meteo> findByVille(String ville);
}