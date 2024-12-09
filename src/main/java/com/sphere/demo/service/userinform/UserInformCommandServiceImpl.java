package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.userinform.UserInformModifyConverter;
import com.sphere.demo.converter.userinform.UserPortfolioConverter;
import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.*;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserPortfolioRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInformCommandServiceImpl implements UserInformCommandService{
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public void modifyInformUser(UserInformRequestDto.ModifyDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        User modifyUser = UserInformModifyConverter.toUserInform(request, user);
        userRepository.save(modifyUser);
    }

    public void addPortfolioUser(UserPortfolioRequestDto.portfolioDto request, Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        PortfolioProject newPortfolio = UserPortfolioConverter.toPortfolioProject(request);
        newPortfolio.setUser(existingUser);
        portfolioRepository.save(newPortfolio);
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
