package com.sphere.demo.service.userinform;

import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.User;

import java.util.List;

public interface UserInformQueryService {
    User findById (Long id);

    List<PortfolioProject> getPortfolioProjectByUserId(Long userId);
}
