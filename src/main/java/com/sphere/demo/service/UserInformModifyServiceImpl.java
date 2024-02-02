package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserInformModifyConverter;
import com.sphere.demo.converter.UserPositionConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.UserPositionRepository;
import com.sphere.demo.repository.UserRepository;
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

    public User ModifyUser(UserInformRequestDto.ModifyDto request){
        User user = UserInformModifyConverter.toUserInform(request);
        List<Position> userPositionList = request.getPositionIdList().stream()
                .map(positionId -> positionRepository.findById(positionId)
                        .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND))
                ).toList();

        List<UserPosition> ResultPositionList = UserPositionConverter.toUserPositionList(userPositionList);

        ResultPositionList.forEach(positions -> {positions.setUser(user);});

        return userRepository.save(user);
    }
}
