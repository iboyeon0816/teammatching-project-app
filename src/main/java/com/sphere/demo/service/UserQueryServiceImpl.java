package com.sphere.demo.service;

import com.sphere.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;


    @Override
    public boolean isDuplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
