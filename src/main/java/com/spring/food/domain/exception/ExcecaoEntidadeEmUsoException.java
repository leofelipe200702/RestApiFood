package com.spring.food.domain.exception;

public class ExcecaoEntidadeEmUsoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExcecaoEntidadeEmUsoException(String debug) {
		super(debug);
	}

}
