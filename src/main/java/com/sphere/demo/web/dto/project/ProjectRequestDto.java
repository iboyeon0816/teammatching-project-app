package com.sphere.demo.web.dto.project;

import com.sphere.demo.domain.enums.ProjectState;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRequestDto {

    @Getter
    public static class ProjectDetailDto {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
        @NotNull
        private LocalDate startDate;
        @NotNull
        private LocalDate endDate;
        @NotNull
        private LocalDate deadline;
        @NotEmpty
        private final List<Long> platformIdList = new ArrayList<>();
        @NotEmpty
        private final List<String> technologyNameList = new ArrayList<>();
        @NotEmpty
        private final List<PositionDto> positionDtoList = new ArrayList<>();
    }

    @Getter
    public static class PositionDto {
        @NotNull
        private Long positionId;
        @NotNull
        @Min(1)
        private Integer memberCount;
    }

    @Getter
    public static class ApplyDto {
        @NotNull
        private Long resumeId;
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
