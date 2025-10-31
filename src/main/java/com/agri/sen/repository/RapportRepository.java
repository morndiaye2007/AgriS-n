package com.agri.sen.repository;

import com.agri.sen.entity.RapportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository extends JpaRepository<RapportEntity, Long> {
}
