package br.org.rafm.exception;

public class UnicityRuleException extends EntityException {

	private static final long serialVersionUID = 1L;

	public UnicityRuleException(String message) {
		super(message);
	}

	public UnicityRuleException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
