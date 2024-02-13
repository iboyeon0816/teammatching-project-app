package com.sphere.demo.service.userinform;

import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.repository.UserPositionRepository;
import com.sphere.demo.repository.UserTechStackRepository;
import com.sphere.demo.service.userinform.UserInformDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class UserInformDeleteServiceImpl implements UserInformDeleteService {
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
