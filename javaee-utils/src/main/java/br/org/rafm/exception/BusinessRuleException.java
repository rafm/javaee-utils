package br.org.rafm.exception;

public class BusinessRuleException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessRuleException(final String message) {
		super(message);
	}
	
	public BusinessRuleException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
