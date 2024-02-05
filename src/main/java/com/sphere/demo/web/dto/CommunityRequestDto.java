package com.sphere.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

public class CommunityRequestDto {

    @Getter
    public static class CreateDto {

        @NotBlank
        private String title;

        private String body;

    }

}
