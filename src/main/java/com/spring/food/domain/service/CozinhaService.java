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
					String.format("Não existe Cozinha com o código %d", idCozinha));
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoEntidadeEmUsoException(
					String.format("A cozinha de código %d está em uso e não pode ser excluída", idCozinha));
		}

	}

}
