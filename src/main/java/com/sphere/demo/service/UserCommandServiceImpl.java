package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserConverter;
import com.sphere.demo.converter.UserPositionConverter;
import com.sphere.demo.converter.UserTechStackConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.TechStackException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.TechStackRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.UserRequestDto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TechStackRepository techStackRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(JoinDto joinDto) {
        User user = toUser(joinDto);

        setPositionToUser(joinDto, user);
        setTechStackToUser(joinDto, user);

        userRepository.save(user);
    }

    private User toUser(JoinDto joinDto) {
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword().trim());
        return UserConverter.toUser(joinDto, encodedPassword);
    }

    private void setPositionToUser(JoinDto joinDto, User user) {
        List<Position> positionList = findPositionEntity(joinDto);
        List<UserPosition> userPositionList = UserPositionConverter.toUserPositionList(positionList);
        userPositionList.forEach(userPosition -> userPosition.setUser(user));
    }

    private List<Position> findPositionEntity(JoinDto joinDto) {
        return joinDto.getPositionIdList().stream().map(
                positionId -> positionRepository.findById(positionId)
                        .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND))
        ).toList();
    }

    private void setTechStackToUser(JoinDto joinDto, User user) {
        List<TechnologyStack> techStackList = findTechStackEntity(joinDto);
        List<UserTechStack> userTechStackList = UserTechStackConverter.toUserTechStack(techStackList);
        userTechStackList.forEach(userTechStack -> userTechStack.setUser(user));
    }

    private List<TechnologyStack> findTechStackEntity(JoinDto joinDto) {
        return joinDto.getTechStackIdList().stream().map(
                techStackId -> techStackRepository.findById(techStackId)
                        .orElseThrow(() -> new TechStackException(ErrorStatus.TECH_STACK_NOT_FOUND))
        ).toList();
    }
}
