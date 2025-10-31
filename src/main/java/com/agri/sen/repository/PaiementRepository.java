package com.agri.sen.repository;

import com.agri.sen.entity.PaiementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<PaiementEntity, Long> {
}
