package com.agri.sen.repository;

import com.webgram.stage.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long>, QuerydslPredicateExecutor<AgentEntity> {

    @Query("SELECT e.matricule FROM AgentEntity e ORDER BY e.matricule DESC LIMIT 1")
    Optional<String> findLastMatricule();

    boolean existsByMatricule(String matricule);
}