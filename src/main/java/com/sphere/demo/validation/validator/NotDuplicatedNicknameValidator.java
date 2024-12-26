package com.sphere.demo.validation.validator;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.service.UserQueryService;
import com.sphere.demo.validation.annotation.NotDuplicatedNickname;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotDuplicatedNicknameValidator implements ConstraintValidator<NotDuplicatedNickname, String> {

    private final UserQueryService userQueryService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = !userQueryService.isDuplicatedNickname(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.DUPLICATED_NICKNAME.getMessage())
                    .addConstraintViolation();
        }
        return isValid;
    }
}
