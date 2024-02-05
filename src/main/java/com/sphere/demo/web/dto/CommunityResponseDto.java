package com.sphere.demo.web.dto;

import com.sphere.demo.domain.enums.ProjectState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class CommunityResponseDto {
    @Getter
    @AllArgsConstructor
    public static class CreateResultDto {
        private Long communityId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityShowDto {

        private String title;

        private String body;

        private Integer view;

        private LocalDate createdAt;

        private Integer totalNumber;

        private String nickname;

    }

}
