package com.sphere.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class UserResponseDto {

    @Builder
    @Getter
    public static class InformResultDto{
        String nickname;
        String email;
        String school;
        String major;
        private List<Long> positionIdList;
        private List<Long> techStackIdList;
//        private List<Long> portfolio;
    }
}
