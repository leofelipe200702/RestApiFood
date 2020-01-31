package com.spring.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.spring.food.domain.entity.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> porNomeAndTaxa(String nome, BigDecimal txInicial, BigDecimal txFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}