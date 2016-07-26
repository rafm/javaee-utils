package br.org.rafm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.rafm.validation.constraints.NotBlankWithSize;

public class NotBlankWithSizeValidator implements ConstraintValidator<NotBlankWithSize, Object> {

	private int min;
	private int max;
	
	@Override
	public void initialize(final NotBlankWithSize notBlankWithSize) {
		min = notBlankWithSize.min() < 1 ? 1 : notBlankWithSize.min();
		max = notBlankWithSize.max();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		if (!(object instanceof String)) {
			return true;
		}
		
		final int length = ((String) object).trim().length();
		
		if (length < min || length > max) {
			return false;
		}
		
		return true;
	}
}
