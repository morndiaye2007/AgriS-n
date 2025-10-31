package com.agri.sen.repository;

import com.agri.sen.entity.AnalyseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyseRepository extends JpaRepository<AnalyseEntity, Long> {
}
