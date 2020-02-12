package com.spring.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.food.domain.entity.Cozinha;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String COZINHA_EM_USO = "A cozinha de código %d está em uso e não pode ser excluída";
	private static final String COZINHA_NAO_ENCONTRADA = "Não existe Cozinha com o código %d";
	@Autowired
	private CozinhaRepository repository;

	public List<Cozinha> list() {
		return repository.findAll();
	}

	public Cozinha save(Cozinha pCozinha) {
		return repository.save(pCozinha);
	}

	public Optional<Cozinha> findById(Long id) {
		return repository.findById(id);
	}

	public void remove(Long idCozinha) {

		try {

			repository.deleteById(idCozinha);

		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format(COZINHA_NAO_ENCONTRADA, idCozinha));
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoEntidadeEmUsoException(
					String.format(COZINHA_EM_USO, idCozinha));
		}

	}

	public Cozinha buscaCozinhaExistente(Long idCozinha) {
		return findById(idCozinha).orElseThrow(() -> new ExcecaoEntidadeNaoEncontradaException(String.format(COZINHA_NAO_ENCONTRADA, idCozinha)));
	}

}
