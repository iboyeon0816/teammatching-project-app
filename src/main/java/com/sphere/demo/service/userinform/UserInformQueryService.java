package com.sphere.demo.service.userinform;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;

import java.util.List;

public interface UserInformQueryService {
    User findById (Long id);

    List<Resume> getPortfolioProjectByUserId(Long userId);
}
