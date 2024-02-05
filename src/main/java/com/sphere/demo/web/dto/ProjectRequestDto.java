package com.sphere.demo.web.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
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

        private List<Long> platformIdList;

        private List<Long> techStackIdList;

        private List<PositionDto> positionInfoList;
    }

    @Getter
    public static class PositionDto {
        private Long positionId;
        private Integer memberCount;
    }
}
