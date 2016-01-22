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
@Constraint(validatedBy = NotBlankInSizeValidator.class)
@Documented
public @interface NotBlankInSize {

    String message() default "{br.org.rafm.constraints.NotBlankInSize}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String value();
    
    int min() default 1;
    
    int max() default Integer.MAX_VALUE;
    
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		NotBlankInSize[] value();
	}
}
