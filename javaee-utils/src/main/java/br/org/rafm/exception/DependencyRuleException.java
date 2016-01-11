package br.org.rafm.exception;

public class DependencyRuleException extends EntityException {

	private static final long serialVersionUID = 1L;

	public DependencyRuleException(String message) {
		super(message);
	}

	public DependencyRuleException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
