package com.sphere.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserInformResponseDto {

    @Builder
    @Getter
    public static class InformResultDto{
        String nickname;
        String email;
        String school;
        String major;
        private List<Long> positionIdList;
        private List<Long> techStackIdList;
        private List<UserPortfolioResponseDto.PortfolioResultDto> portfolio;
    }
}
