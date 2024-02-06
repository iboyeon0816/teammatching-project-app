package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserInformModifyConverter;
import com.sphere.demo.converter.UserPositionConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.UserPositionRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.repository.UserTechStackRepository;
import com.sphere.demo.web.dto.UserInformRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInformModifyServiceImpl {
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final UserPositionRepository userPositionRepository;
    private final UserTechStackRepository userTechStackRepository;

    public User ModifyUser(UserInformRequestDto.ModifyDto request, Long userId){

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        List<Position> positionList = request.getPositionIdList().stream()
                .map(positionId -> positionRepository.findById(positionId)
                        .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND))
                ).toList();

        List<UserPosition> resultPositionList = UserPositionConverter.toUserPositionList(positionList);

        resultPositionList.forEach(positions -> {positions.modifyUser(existingUser);});

        return UserInformModifyConverter.toUserInform(request, existingUser);
    }

    public List<UserPosition> getPositionsByUserId(Long userId) {
        return userPositionRepository.findPositionsByUserId(userId);
    }

    public List<UserTechStack> getTechStacksByUserId(Long userId) {
        return userTechStackRepository.findTechStacksByUserId(userId);
    }

//    public List<UserPosition> getPositionsByUserId(UserInformRequestDto.ModifyDto request, Long userId) {
//
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
//
//        List<Position> userPositionList = request.getPositionIdList().stream()
//                .map(positionId -> positionRepository.findById(positionId)
//                        .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND))
//                ).toList();
//
//        List<UserPosition> ResultPositionList = UserPositionConverter.toUserPositionList(userPositionList);
//
//        ResultPositionList.forEach(positions -> {positions.modifyUser(existingUser);});
//
//        return userPositionRepository.save(positions);
//    }
//
//    public List<UserTechStack> getTechStacksByUserId(Long userId) {
//        return userTechStackRepository.save(userId);
//    }
}
