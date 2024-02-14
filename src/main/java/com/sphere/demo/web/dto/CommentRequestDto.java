package com.sphere.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CommentRequestDto {
    @Getter
    public static class CreateCommentDto {

        @NotBlank
        private String text;

    }
}
