package com.sphere.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class UserPortfolioRequestDto {
    @Getter
    public static class portfolioDto {
        private String technologyStack;

        private String position;

        private String platform;

        private String body;
    }
}
