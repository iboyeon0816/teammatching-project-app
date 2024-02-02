package com.sphere.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

public class UserInformRequestDto {
    @Getter
    public static class ModifyDto {
        @NotBlank
        private String nickname;
        private String email;
        private String school;
        private String major;
        private List<Long> positionIdList;
        private List<Long> techStackIdList;
    }
}
