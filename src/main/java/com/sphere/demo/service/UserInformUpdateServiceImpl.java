package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserPositionConverter;
import com.sphere.demo.converter.UserTechStackConverter;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInformUpdateServiceImpl implements UserInformUpdateService{
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TechStackRepository techStackRepository;

    public void updateUser(UserInformRequestDto.ModifyDto request, Long userId){

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        updatePositionList(request, existingUser);
        updateTechStackList(request, existingUser);
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
