package com.sphere.demo.service.userinform;

import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserPortfolioRequestDto;

public interface UserInformCommandService {
    void modifyInformUser(UserInformRequestDto.ModifyDto request, Long userId);

    void addPortfolioUser(UserPortfolioRequestDto.portfolioDto request, Long userId);

    void deleteUser(Long userId);
}
