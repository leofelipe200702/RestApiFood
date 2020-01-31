package com.spring.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.food.domain.entity.Restaurante;

@Repository
public interface RestauranteRepository
		extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();	
	
	// consulta restaurantes que possuam a taxafrete entre a txinicial e txfinal
	List<Restaurante> findByTaxaFreteBetween(BigDecimal txInicial, BigDecimal txFinal);

	// consulta pelo nome com Like e pelo Id da cozinha
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long idCozinha);

	// a palavra First irá retornar apenas o primeiro registro da consulta
	Optional<Restaurante> findFirstByNomeContaining(String nome);

	// top2 trará apenas os 2 primeiros registros da consulta
	Optional<Restaurante> findTop2ByNomeContaining(String nome);

	// quantidade de restaurantes por cozinha
	long countByCozinhaId(Long idCozinha);

	// método utilizando JPQL com a annottation @query
	// @Query("from Restaurante where nome like %:nome% and cozinha.id = :id" )
	// caso seja comentado a annotation query devemos criar essa query no arquivo
	// orm.xml
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

}
