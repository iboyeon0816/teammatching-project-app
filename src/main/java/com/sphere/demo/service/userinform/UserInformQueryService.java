package com.sphere.demo.service.userinform;

import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;

import java.util.List;

public interface UserInformQueryService {
    public User findById (Long id);
    List<UserPosition> getPositionsByUserId(Long userId);

    List<UserTechStack> getTechStacksByUserId(Long userId);

    List<PortfolioProject> getPortfolioProjectByUserId(Long userId);
}
