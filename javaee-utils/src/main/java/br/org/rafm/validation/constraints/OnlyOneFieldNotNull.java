package br.org.rafm.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.org.rafm.validation.OnlyOneFieldNotNullValidator;

@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OnlyOneFieldNotNullValidator.class)
@Documented
public @interface OnlyOneFieldNotNull {

    String message() default "{br.org.rafm.validation.constraints.OnlyOneFieldNotNull}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String[] value();
    
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		OnlyOneFieldNotNull[] value();
	}
}
