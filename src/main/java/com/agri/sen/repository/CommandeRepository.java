package com.agri.sen.repository;

import com.agri.sen.entity.CommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<CommandeEntity, Long> {
}
