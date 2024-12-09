package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.PortfolioRepository;
import com.sphere.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserInformQueryServiceImpl implements UserInformQueryService{
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public User findById (Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    public List<PortfolioProject> getPortfolioProjectByUserId(Long userId) {
        return portfolioRepository.findPortfoiloProjectByUserId(userId);
    }
}
