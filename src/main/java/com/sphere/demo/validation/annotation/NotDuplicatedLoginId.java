package com.sphere.demo.validation.annotation;

import com.sphere.demo.validation.validator.NotDuplicatedLoginIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotDuplicatedLoginIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDuplicatedLoginId {
    String message() default "이미 존재하는 아이디입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
