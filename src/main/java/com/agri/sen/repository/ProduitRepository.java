//package com.agri.sen.repository;
//
//import com.agri.sen.entity.ProduitEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface ProduitRepository extends JpaRepository<ProduitEntity, Long>, QuerydslPredicateExecutor<ProduitEntity> {
//
//    @Query("SELECT e.nom FROM ProduitEntity e ORDER BY e.nom DESC LIMIT 1")
//    Optional<String> findLastNom();
//
//    boolean existsByNom(String nom);
//}