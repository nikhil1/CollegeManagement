package br.com.pedropareschi.collegemanagement.services.validation;

import br.com.pedropareschi.collegemanagement.services.validation.validator.CEPValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CEPValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CEP {
    String message() default "Invalid cep";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
