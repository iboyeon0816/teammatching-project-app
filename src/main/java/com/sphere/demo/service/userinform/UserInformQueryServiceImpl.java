package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserPositionRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.repository.UserTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserInformQueryServiceImpl implements UserInformQueryService{
    private final UserRepository userRepository;
    private final UserPositionRepository userPositionRepository;
    private final UserTechStackRepository userTechStackRepository;

    public User findById (Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        return user;
    }

    public List<UserPosition> getPositionsByUserId(Long userId) {
        return userPositionRepository.findPositionsByUserId(userId);
    }

    public List<UserTechStack> getTechStacksByUserId(Long userId) {
        return userTechStackRepository.findTechStacksByUserId(userId);
    }
}
