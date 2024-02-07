package com.sphere.demo.web.dto;


import com.sphere.demo.domain.enums.ProjectState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRequestDto {

    @Getter
    public static class CreateDto {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate deadline;
        private List<Long> platformIdList = new ArrayList<>();
        private List<Long> techStackIdList = new ArrayList<>();
        private List<PositionDto> positionDtoList = new ArrayList<>();
    }

    @Getter
    public static class PositionDto {
        private Long positionId;
        private Integer memberCount;
    }

    @Getter
    public static class UpdateDto {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate deadline;
    }

    @Getter
    public static class ApplyDto {
        @NotNull
        private Long positionId;
    }

    @Getter
    public static class ProjectSearchCond {
        private String positionName;
        private String techStackName;
        private String platformName;
        private ProjectState projectState;
        private String title;
    }
}
