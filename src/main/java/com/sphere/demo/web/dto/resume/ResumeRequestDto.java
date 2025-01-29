package com.sphere.demo.web.dto.resume;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeRequestDto {
    @Getter
    public static class ResumeDetailDto {

        @NotBlank
        private String title;

        @NotBlank
        private String name;

        @NotNull
        private LocalDate birthDate;

        @NotBlank
        private String position;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String aboutMe;

        @NotBlank
        private String techStacks;

        private String awards;

        private final List<ResumeProjectDetailDto> resumeProjectDetailDtoList = new ArrayList<>();
    }

    @Getter
    public static class ResumeProjectDetailDto {
        @NotBlank
        private String title;

        @NotNull
        private LocalDate startDate;

        @NotNull
        private LocalDate endDate;

        @NotBlank
        private String techStacks;

        @NotBlank
        private String serviceContents;

        @NotBlank
        private String devDetails;

        @NotBlank
        private String growthDetails;

        private String relativeActivities;
    }
}
