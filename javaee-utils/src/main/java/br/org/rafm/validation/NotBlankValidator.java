package br.org.rafm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.rafm.validation.constraints.NotBlank;

public class NotBlankValidator implements ConstraintValidator<NotBlank, Object> {

	private int min;
	
	@Override
	public void initialize(final NotBlank notBlank) {
		min = notBlank.min() < 1 ? 1 : notBlank.min();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		if (!(object instanceof String)) {
			return true;
		}
		
		final int length = ((String) object).trim().length();
		
		if (length < min) {
			return false;
		}
		
		return true;
	}
}
