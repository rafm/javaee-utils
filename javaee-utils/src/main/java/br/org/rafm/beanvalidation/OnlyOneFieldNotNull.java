package br.org.rafm.beanvalidation;

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

@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OnlyOneFieldNotNullValidator.class)
@Documented
public @interface OnlyOneFieldNotNull {

    String message() default "{br.uerj.cepuerj.constraints.XORNotNull}";

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
