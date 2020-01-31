package com.spring.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.food.domain.entity.Cidade;
import com.spring.food.domain.entity.Estado;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.repository.CidadeRepository;
import com.spring.food.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repositoryCidade;

	@Autowired
	private EstadoRepository repositoryEstado;

	public List<Cidade> getList() {
		return repositoryCidade.findAll();
	}

	public Optional<Cidade> findById(Long idCidade) {
		return repositoryCidade.findById(idCidade);
	}

	public Cidade save(Cidade cidade) {

		Estado estado = repositoryEstado.findById(cidade.getEstado().getId()).orElseThrow(() -> {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format("O estado de código %d não existe", cidade.getEstado().getId()));
		});

		cidade.setEstado(estado);

		return repositoryCidade.save(cidade);
	}

	public void remove(Long idCidade) {
		try {

			repositoryCidade.deleteById(idCidade);

		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format("Não existe cidade com o código %d", idCidade));
		}
	}

}
