package com.sphere.demo.web.dto;

import com.sphere.demo.web.dto.resume.ResumeResponseDto;
import lombok.Builder;
import lombok.Getter;

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
        private List<ResumeResponseDto.PortfolioResultDto> portfolio;
    }
}
