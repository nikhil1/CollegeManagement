package br.com.pedropareschi.collegemanagement.services.validation;

import br.com.pedropareschi.collegemanagement.services.validation.validator.UniqueValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "Unique=";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
