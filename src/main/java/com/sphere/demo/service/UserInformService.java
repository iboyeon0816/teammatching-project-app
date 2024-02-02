package com.sphere.demo.service;

import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;

import java.util.List;

public interface UserInformService {
    User findById (Long id);
    List<UserPosition> getPositionsByUserId(Long userId);

    List<UserTechStack> getTechStacksByUserId(Long userId);
}
