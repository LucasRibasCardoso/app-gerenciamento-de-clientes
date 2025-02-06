package com.agencia.backend.presentation.validators.client;

import com.agencia.backend.presentation.validators.client.implementation.NotEmptyIfNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyIfNotNullValidator.class)
public @interface NotEmptyIfNotNull {
  String message() default "O campo não pode ser vazio.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
