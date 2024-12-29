package com.sphere.demo.web.dto.project;

import com.sphere.demo.domain.enums.ProjectState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDto {

    @Getter
    @AllArgsConstructor
    public static class CreateResultDto {
        private Long projectId;
    }

    @Getter
    @AllArgsConstructor
    public static class ApplyResultDto {
        private Long projectApplicationId;
    }

    @Getter
    @AllArgsConstructor
    public static class FavoriteResultDto {
        private boolean isFavorite;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProjectPageDto {
        private List<GetResultDto> getResultDtoList;
        private Integer listSize;
        private Boolean isFirst;
        private Boolean isLast;
        private Integer totalPages;
        private Long totalElements;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetResultDto {
        private Long projectId;
        private String writerNickname;
        private String title;
        private ProjectState projectState;
        private LocalDate deadline;
        private Integer views;
        private List<String> positionNameList;
        private List<String> techStackNameList;
        private List<String> platformNameList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProjectDetailDto {
        private String writerNickname;
        private String title;
        private String body;
        private ProjectState projectState;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate createdAt;
        private LocalDate deadline;
        private Integer views;
        private Integer totalRecruitNumber;
        private List<String> techStackNameList;
        private List<String> platformNameList;
        private List<PositionDetailDto> positionDetailDtoList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PositionDetailDto {
        private String positionName;
        private Integer totalNumber;
        private Integer matchedNumber;
    }
}
