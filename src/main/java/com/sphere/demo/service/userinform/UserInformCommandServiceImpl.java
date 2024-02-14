package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.userinform.UserInformModifyConverter;
import com.sphere.demo.converter.UserPositionConverter;
import com.sphere.demo.converter.UserTechStackConverter;
import com.sphere.demo.converter.userinform.UserPortfolioConverter;
import com.sphere.demo.domain.PortfolioProject;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.TechStackException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.*;
import com.sphere.demo.web.dto.UserInformRequestDto;
import com.sphere.demo.web.dto.UserPortfolioRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInformCommandServiceImpl implements UserInformCommandService{
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TechStackRepository techStackRepository;
    private final UserPositionRepository userPositionRepository;
    private final UserTechStackRepository userTechStackRepository;
    private final PortfolioRepository portfolioRepository;

    public void modifyInformUser(UserInformRequestDto.ModifyDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        User modifyUser = UserInformModifyConverter.toUserInform(request, user);
        userRepository.save(modifyUser);
    }

    public void deleteInformUser(Long userId){
        List<UserPosition> userPositions = userPositionRepository.findPositionsByUserId(userId);

        for (UserPosition userPosition : userPositions) {
            userPosition.deletePosition();
            userPositionRepository.delete(userPosition);
        }

        List<UserTechStack> userTechStacks = userTechStackRepository.findTechStacksByUserId(userId);

        for (UserTechStack userTechStack : userTechStacks) {
            userTechStack.deleteTechStack();
            userTechStackRepository.delete(userTechStack);
        }
    }

    public void updateInformUser(UserInformRequestDto.ModifyDto request, Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        updatePositionList(request, existingUser);
        updateTechStackList(request, existingUser);
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

    public void updatePositionList(UserInformRequestDto.ModifyDto request, User existingUser){
        List<Position> positionList = request.getPositionIdList().stream()
                .map(positionId -> positionRepository.findById(positionId)
                        .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND))
                ).toList();

        List<UserPosition> resultPositionList = UserPositionConverter.toUserPositionList(positionList);

        resultPositionList.forEach(userPosition -> userPosition.setUser(existingUser));
    }

    public void updateTechStackList(UserInformRequestDto.ModifyDto request, User existingUser){
        List<TechnologyStack> techStackList = request.getTechStackIdList().stream()
                .map(techStackId -> techStackRepository.findById(techStackId)
                        .orElseThrow(() -> new TechStackException(ErrorStatus.TECH_STACK_NOT_FOUND))
                ).toList();

        List<UserTechStack> resultTechStackList = UserTechStackConverter.toUserTechStack(techStackList);

        resultTechStackList.forEach(userTechStack -> userTechStack.setUser(existingUser));
    }
}
