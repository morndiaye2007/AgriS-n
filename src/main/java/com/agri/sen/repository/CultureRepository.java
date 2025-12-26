package com.agri.sen.repository;

import com.agri.sen.entity.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long>,QuerydslPredicateExecutor<Culture>{
}
