package com.jm.orion.exception;

public class LowValueException extends Exception {

	/**
	 * Constructor for QuickPayCustomException.
	 **/
	public LowValueException(){
		super();
	}

	public LowValueException(String message, Throwable cause) {
		super(message,cause);

	}

	public LowValueException(Throwable cause) {
		super(cause);

	}

	public LowValueException(String message) {
		super(message);

	}
}



