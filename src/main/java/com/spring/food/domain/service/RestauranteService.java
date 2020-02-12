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

	private static final String RESTAURANTE_EM_USO = "O restaurante de código %d está em uso e não pode ser excluído";

	private static final String COZINHA_NÃO_EXISTE = "A cozinha de código %d não existe";

	private static final String RESTAURANTE_INEXISTENTE = "Não existe restaurante com o código %d";

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
					String.format(COZINHA_NÃO_EXISTE, pRestaurante.getCozinha().getId()));
		});

		pRestaurante.setCozinha(cozinha);

		return repositoryRestaurante.save(pRestaurante);
	}

	public void remove(Long idRestaurante) {

		try {

			repositoryRestaurante.deleteById(idRestaurante);

		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format(RESTAURANTE_INEXISTENTE, idRestaurante));
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoEntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, idRestaurante));
		}

	}
	
	public Restaurante buscaRestauranteExistente(Long idRestaurante) {
		return findById(idRestaurante).orElseThrow(() -> new ExcecaoEntidadeNaoEncontradaException(String.format(RESTAURANTE_INEXISTENTE, idRestaurante)));
	}

}
