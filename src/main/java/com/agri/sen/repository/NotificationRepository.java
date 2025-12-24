package com.agri.sen.repository;

import com.agri.sen.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, QuerydslPredicateExecutor<Notification> {

    // Liste des notifications non lues pour un utilisateur donné
    @Query("SELECT n FROM Notification n WHERE n.utilisateurId = :utilisateurId AND n.lu = false")
    List<Notification> findByUserIdAndLuFalse(@Param("utilisateurId") Long utilisateurId);

    // Compte le nombre de notifications non lues pour un utilisateur donné
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.utilisateurId = :utilisateurId AND n.lu = false")
    Long countUnreadByUser(@Param("utilisateurId") Long utilisateurId);
}
