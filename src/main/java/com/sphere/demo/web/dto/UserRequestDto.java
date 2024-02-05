package com.sphere.demo.web.dto;

import com.sphere.demo.validation.annotation.NotDuplicatedLoginId;
import com.sphere.demo.validation.annotation.NotDuplicatedNickname;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class UserRequestDto {

    @Getter
    public static class JoinDto {

        @NotBlank
        @NotDuplicatedLoginId
        private String loginId;

        @NotBlank
        private String password;

        @NotBlank
        @NotDuplicatedNickname
        private String nickname;

        // 임시 필드
        private String email;
        // 임시 필드
        private String school;
        // 임시 필드
        private String major;

        private List<Long> positionIdList;

        private List<Long> techStackIdList;

    }

    @Getter
    public static class ApplyDto {
        @NotNull
        private Long positionId;
    }
}
