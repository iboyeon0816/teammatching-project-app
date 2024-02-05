package com.sphere.demo.validation.annotation;

import com.sphere.demo.validation.validator.NotDuplicatedNicknameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotDuplicatedNicknameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDuplicatedNickname {

    String message() default "이미 존재하는 닉네임입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
