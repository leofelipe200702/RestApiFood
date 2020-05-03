package com.spring.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.food.domain.entity.Cozinha;
import com.spring.food.domain.entity.Restaurante;
import com.spring.food.domain.exception.EntidadeEmUsoException;
import com.spring.food.domain.exception.RestauranteNaoEncontradoException;
import com.spring.food.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String RESTAURANTE_EM_USO = "O restaurante de código %d está em uso e não pode ser excluído";

	@Autowired
	private RestauranteRepository repositoryRestaurante;

	@Autowired
	private CozinhaService serviceCozinha;

	public List<Restaurante> getList() {
		return repositoryRestaurante.findAll();
	}

	public Optional<Restaurante> findById(Long idRestaurante) {
		return repositoryRestaurante.findById(idRestaurante);
	}

	public Restaurante save(Restaurante pRestaurante) {

		Cozinha cozinha = serviceCozinha.buscaCozinhaExistente(pRestaurante.getCozinha().getId());
		
		pRestaurante.setCozinha(cozinha);

		return repositoryRestaurante.save(pRestaurante);
	}

	public void remove(Long idRestaurante) {

		try {

			repositoryRestaurante.deleteById(idRestaurante);

		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(idRestaurante);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, idRestaurante));
		}

	}
	
	public Restaurante buscaRestauranteExistente(Long idRestaurante) {
		return findById(idRestaurante).orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
	}

}
