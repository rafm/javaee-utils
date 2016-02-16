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
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Mod11Check;

@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy={})
@Pattern.List(value={@Pattern(regexp="(\\d{11})"), @Pattern(regexp="^(?:(?![0]{11}).)*$"), @Pattern(regexp="^(?:(?![1]{11}).)*$"), @Pattern(regexp="^(?:(?![2]{11}).)*$"), @Pattern(regexp="^(?:(?![3]{11}).)*$"), @Pattern(regexp="^(?:(?![4]{11}).)*$"), @Pattern(regexp="^(?:(?![5]{11}).)*$"), @Pattern(regexp="^(?:(?![6]{11}).)*$"), @Pattern(regexp="^(?:(?![7]{11}).)*$"), @Pattern(regexp="^(?:(?![8]{11}).)*$"), @Pattern(regexp="^(?:(?![9]{11}).)*$")})
@Mod11Check.List(value={@Mod11Check(checkDigitIndex=9, endIndex=8, treatCheck10As='0'), @Mod11Check(checkDigitIndex=10, endIndex=9, treatCheck10As='0')})
@ReportAsSingleViolation
@Documented
public @interface CPF {
	
    String message() default "{br.org.rafm.validation.constraints.CPF}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		CPF[] value();
	}
}
