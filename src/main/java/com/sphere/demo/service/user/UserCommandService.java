package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(User user, String password) {
        validateNickname(user.getNickname());
        String encodedPassword = passwordEncoder.encode(password.trim());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    private void validateNickname(String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        if (exists) {
            throw new UserException(ErrorStatus.DUPLICATED_NICKNAME);
        }
    }

}
