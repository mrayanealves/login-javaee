package br.ufrn.imd.logan.exception;

@SuppressWarnings("serial")
public class LoginException extends RuntimeException{

	public LoginException() {
		super();
	}

	public LoginException(String message) {
		super(message);
	}
}
