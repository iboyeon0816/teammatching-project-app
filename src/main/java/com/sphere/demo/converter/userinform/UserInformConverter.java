package com.sphere.demo.converter.userinform;

import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserInformResponseDto;
import com.sphere.demo.web.dto.UserPortfolioResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserInformConverter {
    public static UserInformResponseDto.InformResultDto toInformResultDto(User user, List<PortfolioProject> portfolioProjects){


        List<UserPortfolioResponseDto.PortfolioResultDto> portfolioList = portfolioProjects.stream()
                .map(portfolioProject -> {
                    UserPortfolioResponseDto.PortfolioResultDto dto = new UserPortfolioResponseDto.PortfolioResultDto();
                    dto.setTechnologyStack(portfolioProject.getTechnologyStack());
                    dto.setPosition(portfolioProject.getPosition());
                    dto.setPlatform(portfolioProject.getPlatform());
                    dto.setBody(portfolioProject.getBody());
                    return dto;
                })
                .collect(Collectors.toList());


        return UserInformResponseDto.InformResultDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .portfolio(portfolioList)
                .build();
    }

}
