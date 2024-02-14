package com.sphere.demo.converter;

import com.sphere.demo.domain.Comment;
import com.sphere.demo.web.dto.CommentRequestDto;

public class CommentConverter {
    public static Comment toComment(CommentRequestDto.CreateCommentDto createCommentDto) {
        return Comment.builder()
                .text(createCommentDto.getText())
                .build();
    }
}
