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

import br.org.rafm.validation.NotBlankWithSizeValidator;

@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankWithSizeValidator.class)
@Documented
public @interface NotBlankWithSize {

    String message() default "{br.org.rafm.validation.constraints.NotBlankWithSize}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    int min() default 1;
    
    int max() default Integer.MAX_VALUE;
    
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		NotBlankWithSize[] value();
	}
}
