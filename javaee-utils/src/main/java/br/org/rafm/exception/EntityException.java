package br.org.rafm.exception;

public class EntityException extends BusinessRuleException {

	private static final long serialVersionUID = 1L;

	public EntityException(String message) {
		super(message);
	}

	public EntityException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
