package com.sphere.demo.web.dto;

import com.sphere.demo.domain.enums.ProjectState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDto {

    @Getter
    @AllArgsConstructor
    public static class CreateResultDto {
        private Long projectId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDetailDto {

        private String title;

        private String body;

        private LocalDate startDate;

        private LocalDate endDate;

        private Integer view;

        private ProjectState status;

        private LocalDate createdAt;

        private LocalDate deadline;

        private Integer totalNumber;

        private String nickname;

        private List<String> platformList;

        private List<String> techStackList;

        private List<PositionDetailDto> positionDetailDtoList;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionDetailDto {
        private String positionName;
        private Integer totalNumber;
        private Integer recruitedNumber;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDto {
        private String creatorNickname;
        private String title;
        private List<String> positionList;
        private List<String> techStackList;
        private List<String> platformList;
        private LocalDate deadline;
        private Integer views;
        private ProjectState projectState;
    }
}
