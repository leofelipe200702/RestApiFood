package com.spring.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.food.domain.entity.Cozinha;
import com.spring.food.domain.entity.Restaurante;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.repository.CozinhaRepository;
import com.spring.food.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository repositoryRestaurante;

	@Autowired
	private CozinhaRepository repositoryCozinha;

	public List<Restaurante> getList() {
		return repositoryRestaurante.findAll();
	}

	public Optional<Restaurante> findById(Long idRestaurante) {
		return repositoryRestaurante.findById(idRestaurante);
	}

	public Restaurante save(Restaurante pRestaurante) {

		Cozinha cozinha = repositoryCozinha.findById(pRestaurante.getCozinha().getId()).orElseThrow(() -> {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format("A cozinha de código %d não existe", pRestaurante.getCozinha().getId()));
		});

		pRestaurante.setCozinha(cozinha);

		return repositoryRestaurante.save(pRestaurante);
	}

	public void remove(Long idRestaurante) {

		try {

			repositoryRestaurante.deleteById(idRestaurante);

		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format("Não existe restaurante com o código %d", idRestaurante));
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoEntidadeEmUsoException(
					String.format("O restaurante de código %d está em uso e não pode ser excluído", idRestaurante));
		}

	}

}
