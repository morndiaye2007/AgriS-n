package com.agri.sen.repository;

import com.agri.sen.entity.RecommandationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommandationRepository extends JpaRepository<RecommandationEntity, Long> {
}
