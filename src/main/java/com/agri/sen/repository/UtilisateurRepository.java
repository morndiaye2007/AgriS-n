// UtilisateurRepository.java
package com.agri.sen.repository;

import com.agri.sen.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, QuerydslPredicateExecutor<Utilisateur> {
    Optional<Utilisateur> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByTelephone(String telephone);
}