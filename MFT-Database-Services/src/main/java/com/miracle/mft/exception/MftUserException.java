package com.miracle.mft.exception;

import org.springframework.stereotype.Component;

public class MftUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String exceptionMessage;

	public MftUserException(String exceptionMessage, Exception e) {
		super(exceptionMessage, e);

	}

}
