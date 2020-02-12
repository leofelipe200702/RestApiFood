package com.spring.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND/*, reason = "Entidade não encontrada!"*/ )
public class ExcecaoEntidadeNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoEntidadeNaoEncontradaException(String debug) {
		super(debug);
	}

}
