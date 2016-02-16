package br.org.rafm.validation;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.rafm.validation.constraints.OnlyOneFieldNotNull;

public class OnlyOneFieldNotNullValidator implements ConstraintValidator<OnlyOneFieldNotNull, Object> {

	private String[] fieldNames;
	
	@Override
	public void initialize(final OnlyOneFieldNotNull xorNotNull) {
		fieldNames = xorNotNull.value();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		boolean isAnyFieldNotNull = false;
		try {
			for (String fieldName: fieldNames) {
				final Field field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				final Object fieldValue = field.get(object);
				
				if (fieldValue != null) {
					if (isAnyFieldNotNull) {
						return false;
					} else {
						isAnyFieldNotNull = true;
					}
				}
			}
			
			return isAnyFieldNotNull;
		} catch (Exception e) {
			return false;
		}
	}
}
