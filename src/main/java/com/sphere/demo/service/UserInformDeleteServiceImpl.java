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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class UserInformDeleteServiceImpl implements UserInformDeleteService{
    private final UserPositionRepository userPositionRepository;
    private final UserTechStackRepository userTechStackRepository;
    public void deleteUser(Long userId){
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
}
