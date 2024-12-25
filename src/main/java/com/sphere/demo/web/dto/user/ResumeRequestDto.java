package com.sphere.demo.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ResumeRequestDto {
    @Getter
    public static class ResumeDetailDto {

        @NotBlank
        @Email
        private String email;

        @NotNull
        private Long positionId;

        @NotEmpty
        private final List<String> technologyNameList = new ArrayList<>();

        @NotBlank
        private String selfIntroduction;
    }
}
