package com.spring.food.customized.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.spring.food.customized.repository.spec.RestauranteSpecs;
import com.spring.food.domain.entity.Restaurante;
import com.spring.food.domain.repository.RestauranteRepository;
import com.spring.food.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> porNomeAndTaxa(String nome, BigDecimal txInicial, BigDecimal txFinal) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
			predicates.add(nomePredicate);
		}
		
		if(txInicial != null) {
			Predicate txInicialFretePredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"),txInicial);
			predicates.add(txInicialFretePredicate);
		}
		
		if(txFinal != null) {
			Predicate txFinalFretePredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"),txFinal);
			predicates.add(txFinalFretePredicate);
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeLike(nome)));
	}

	/*
	 * @Override public List<Restaurante> porNomeAndTaxa(String nome, BigDecimal
	 * txInicial, BigDecimal txFinal) {
	 * 
	 * var jpql = new StringBuilder();
	 * 
	 * jpql.append("from Restaurante where 0 = 0 ");
	 * 
	 * HashMap<String, Object> parametros = new HashMap<>();
	 * 
	 * if (StringUtils.hasLength(nome)) { jpql.append("and nome like :nome ");
	 * parametros.put("nome", "%" + nome + "%"); }
	 * 
	 * if (txInicial != null) { jpql.append("and taxaFrete >= :txInicial ");
	 * parametros.put("txInicial", txInicial); }
	 * 
	 * if (txFinal != null) { jpql.append("and taxaFrete <= :txFinal ");
	 * parametros.put("txFinal", txFinal); }
	 * 
	 * TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(),
	 * Restaurante.class);
	 * 
	 * parametros.forEach((chave, valor) -> { query.setParameter(chave, valor); });
	 * 
	 * return query.getResultList();
	 * 
	 * }
	 */

}
