package com.sphere.demo.converter.userinform;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserInformResponseDto;
import com.sphere.demo.web.dto.user.ResumeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserInformConverter {
    public static UserInformResponseDto.InformResultDto toInformResultDto(User user, List<Resume> resumes){


        List<ResumeResponseDto.PortfolioResultDto> portfolioList = resumes.stream()
                .map(portfolioProject -> {
                    //                    dto.setTechnologyStack(portfolioProject.getTechnologyStack());
//                    dto.setPosition(portfolioProject.getPosition());
//                    dto.setPlatform(portfolioProject.getPlatform());
//                    dto.setBody(portfolioProject.getBody());
                    return new ResumeResponseDto.PortfolioResultDto();
                })
                .collect(Collectors.toList());


        return UserInformResponseDto.InformResultDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .portfolio(portfolioList)
                .build();
    }

}
