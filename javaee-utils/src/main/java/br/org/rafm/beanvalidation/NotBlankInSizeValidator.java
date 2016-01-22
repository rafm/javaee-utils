package br.org.rafm.beanvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankInSizeValidator implements ConstraintValidator<NotBlankInSize, Object> {

	private String value;
	private int min;
	private int max;
	
	@Override
	public void initialize(final NotBlankInSize notBlankInSize) {
		value = notBlankInSize.value();
		min = notBlankInSize.min() < 1 ? 1 : notBlankInSize.min();
		max = notBlankInSize.max();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		
		int length = value.trim().length();
		
		if (length < min || length > max) {
			return false;
		}
		
		return true;
	}
}
