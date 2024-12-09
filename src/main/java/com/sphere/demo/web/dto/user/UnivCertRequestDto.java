package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UnivCertRequestDto {
    @Getter
    public static class VerifyDto {
        @NotBlank
        private String email;
        @NotBlank
        private String univName;
    }

    @Getter
    public static class ConfirmDto {
        @NotBlank
        private String email;
        @NotBlank
        private String univName;
        @NotNull
        private Integer code;
    }
}
