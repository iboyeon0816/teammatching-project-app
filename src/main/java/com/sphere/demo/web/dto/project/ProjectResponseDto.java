package com.sphere.demo.web.dto.project;

import com.sphere.demo.domain.enums.ProjectState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        private List<ProjectCardDto> projectCardDtoList;
        private Integer listSize;
        private Boolean isFirst;
        private Boolean isLast;
        private Integer totalPages;
        private Long totalElements;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProjectCardDto {
        private Long projectId;
        private String title;
        private String imageUrl;
        private ProjectState projectState;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer viewCount;
        private Integer favoriteCount;
        private Boolean isFavorite;
        private List<PositionDetailDto> positionList;
        private List<String> techNameList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetDetailDto {
        private String title;
        private String writerNickname;
        private String body;
        private String imageUrl;
        private ProjectState projectState;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate deadline;
        private Integer viewCount;
        private Integer favoriteCount;
        private Boolean isFavorite;
        private Boolean isOwner;
        private LocalDateTime createdAt;
        private List<String> platformNameList;
        private List<String> techNameList;
        private List<PositionDetailDto> positionList; //
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
