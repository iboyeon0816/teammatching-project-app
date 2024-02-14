package com.sphere.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

public class UserPortfolioResponseDto {
    @Getter
    @Setter
    public static class PortfolioResultDto {
        private String technologyStack;

        private String position;

        private String platform;

        private String body;
    }
}
