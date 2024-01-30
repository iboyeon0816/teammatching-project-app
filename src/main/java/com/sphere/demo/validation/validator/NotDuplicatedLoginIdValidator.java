package com.sphere.demo.validation.validator;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.service.UserQueryService;
import com.sphere.demo.validation.annotation.NotDuplicatedLoginId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotDuplicatedLoginIdValidator implements ConstraintValidator<NotDuplicatedLoginId, String> {

    private final UserQueryService userQueryService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = !userQueryService.isDuplicatedLoginId(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.USER_DUPLICATED_LOGIN_ID.getMessage())
                    .addConstraintViolation();
        }
        return isValid;
    }
}
