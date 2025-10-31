package com.agri.sen.repository;

import com.agri.sen.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Long> {
}
