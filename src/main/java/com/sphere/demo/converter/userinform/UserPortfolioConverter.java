package com.sphere.demo.converter.userinform;

import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.web.dto.UserPortfolioRequestDto;

public class UserPortfolioConverter {
    public static PortfolioProject toPortfolioProject(UserPortfolioRequestDto.portfolioDto request){
        return PortfolioProject.builder()
                .technologyStack(request.getTechnologyStack())
                .position(request.getPosition())
                .platform(request.getPlatform())
                .body(request.getBody())
                .build();
    }
}
