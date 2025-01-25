package com.sphere.demo.web.dto.project;

import com.sphere.demo.web.dto.enums.ApplicationStateRequest;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

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
        @Future
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
    public static class ApproveDto {
        @NotNull
        private ApplicationStateRequest applicationStateRequest;
    }

    @Getter
    @Setter
    public static class ProjectSearchCond {
        private String position;
        private String tech;
        private Boolean recruiting;
        private String title;
    }
}
