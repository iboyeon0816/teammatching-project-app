package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UnivCertRequestDto {
    @Getter
    public static class VerifyDto {
        @Email
        @NotBlank
        private String univEmail;
        @NotBlank
        private String univName;
    }

    @Getter
    public static class ConfirmDto {
        @Email
        @NotBlank
        private String univEmail;
        @NotBlank
        private String univName;
        @NotNull
        private Integer code;
    }
}
