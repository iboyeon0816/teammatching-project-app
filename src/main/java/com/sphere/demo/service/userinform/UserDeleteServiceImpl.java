package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService{
    private final UserRepository userRepository;

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
