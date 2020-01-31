package com.spring.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.food.domain.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long> {
	
}
