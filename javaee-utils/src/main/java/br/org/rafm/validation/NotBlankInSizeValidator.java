package br.org.rafm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.rafm.validation.constraints.NotBlankInSize;

public class NotBlankInSizeValidator implements ConstraintValidator<NotBlankInSize, Object> {

	private int min;
	private int max;
	
	@Override
	public void initialize(final NotBlankInSize notBlankInSize) {
		min = notBlankInSize.min() < 1 ? 1 : notBlankInSize.min();
		max = notBlankInSize.max();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		if (!(object instanceof String)) {
			return true;
		}
		
		int length = ((String) object).trim().length();
		
		if (length < min || length > max) {
			return false;
		}
		
		return true;
	}
}
