package com.sphere.demo.service.user;

import com.sphere.demo.domain.User;
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
        String encodedPassword = passwordEncoder.encode(password.trim());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
