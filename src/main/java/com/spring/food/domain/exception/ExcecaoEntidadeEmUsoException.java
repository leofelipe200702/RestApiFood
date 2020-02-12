package com.spring.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT/*, reason = "Entidade em uso!"*/ )
public class ExcecaoEntidadeEmUsoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoEntidadeEmUsoException(String debug) {
		super(debug);
	}

}
