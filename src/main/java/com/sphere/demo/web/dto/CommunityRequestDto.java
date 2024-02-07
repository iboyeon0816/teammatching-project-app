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

    @Getter
    public static class UpdateDto {

        private String title;

        private String body;

        @Override
        public String toString() {
            return "CommunityRequestDto.UpdateDto{" +
                    "title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }

    }



}
