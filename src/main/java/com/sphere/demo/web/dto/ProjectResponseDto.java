package com.sphere.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProjectResponseDto {

    @Getter
    @AllArgsConstructor
    public static class CreateResultDto {
        private Long projectId;
    }
}
