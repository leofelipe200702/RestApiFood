package com.spring.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.food.domain.entity.Estado;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String ESTADO_EM_USO = "O estado de código %d está em uso e não pode ser excluído";
	private static final String ESTADO_INEXISTENTE = "Não existe estado com o código %d";
	@Autowired
	private EstadoRepository repositoryEstado;

	public List<Estado> listar() {
		return repositoryEstado.findAll();
	}

	public Optional<Estado> findById(Long idEstado) {
		return repositoryEstado.findById(idEstado);
	}

	public Estado save(Estado estado) {
		return repositoryEstado.save(estado);
	}

	public void remove(Long idEstado) {

		try {

			repositoryEstado.deleteById(idEstado);

		} catch (EmptyResultDataAccessException e) {
			throw new ExcecaoEntidadeNaoEncontradaException(
					String.format(ESTADO_INEXISTENTE, idEstado));
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoEntidadeEmUsoException(
					String.format(ESTADO_EM_USO, idEstado));
		}

	}
	
	public Estado buscaEstadoeExistente(Long idEstado) {
		return findById(idEstado).orElseThrow(() -> new ExcecaoEntidadeNaoEncontradaException(String.format(ESTADO_INEXISTENTE, idEstado)));
	}

}
