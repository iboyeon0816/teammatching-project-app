package com.sphere.demo.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ResumeResponseDto {

    @Getter
    @AllArgsConstructor
    public static class AddResultDto {
        private Long resumeId;
    }

    @Getter
    @Setter
    public static class PortfolioResultDto {
        private String technologyStack;

        private String position;

        private String platform;

        private String body;
    }
}
