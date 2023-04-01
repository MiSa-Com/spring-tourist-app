package com.ms.tourist_app.application.annotation;

import com.ms.tourist_app.application.annotation.validator.CheckUserValidator;

import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = CheckUserValidator.class)
public @interface CheckUser {

    String message() default "Request is invalid!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

}
