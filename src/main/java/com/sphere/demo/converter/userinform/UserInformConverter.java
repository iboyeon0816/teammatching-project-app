package com.sphere.demo.converter.userinform;

import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.web.dto.UserInformResponseDto;
import com.sphere.demo.web.dto.UserPortfolioRequestDto;
import com.sphere.demo.web.dto.UserPortfolioResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserInformConverter {
    public static UserInformResponseDto.InformResultDto toInformResultDto(User user, List<UserPosition> positions, List<UserTechStack> techStacks, List<PortfolioProject> portfolioProjects){
        List<Long> positionIdList = positions.stream()
                .map(UserPosition::getPosition)
                .map(Position::getId)
                .collect(Collectors.toList());

        List<Long> teckStackIdList = techStacks.stream()
                .map(UserTechStack::getTechnologyStack)
                .map(TechnologyStack::getId)
                .collect(Collectors.toList());

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
                .school(user.getSchool())
                .major(user.getMajor())
                .positionIdList(positionIdList)
                .techStackIdList(teckStackIdList)
                .portfolio(portfolioList)
                .build();
    }

}
