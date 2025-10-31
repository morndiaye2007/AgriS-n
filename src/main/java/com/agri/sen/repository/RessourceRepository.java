package com.agri.sen.repository;

import com.agri.sen.entity.RessourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRepository extends JpaRepository<RessourceEntity, Long> {
}
