package com.sphere.demo.web.dto.project;

import com.sphere.demo.domain.enums.ProjectState;
import jakarta.validation.constraints.*;
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
        @NotNull
        private LocalDate startDate;
        @NotNull
        private LocalDate endDate;
        @NotNull
        private LocalDate deadline;
        @NotEmpty
        private final List<Long> platformIdList = new ArrayList<>();
        @NotEmpty
        private final List<Long> technologyIdList = new ArrayList<>();
        @NotEmpty
        private final List<PositionDto> positionDtoList = new ArrayList<>();

        @AssertTrue(message = "startDate는 endDate보다 이전이어야 합니다.")
        public boolean isStartDateBeforeEndDate() {
            return startDate.isBefore(endDate);
        }

        @AssertTrue(message = "deadline은 오늘 이후여야 합니다.")
        public boolean isDeadlineAfterToday() {
            return deadline.isAfter(LocalDate.now());
        }
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
