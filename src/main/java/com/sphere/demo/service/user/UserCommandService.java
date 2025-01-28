package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.JoinDto;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.UpdateDto;
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

    public void join(JoinDto joinDto) {
        validateNickname(joinDto.getNickname());
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword().trim());
        User user = new User(joinDto, encodedPassword);
        userRepository.save(user);
    }

    public void updateUser(Long userId, UpdateDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        user.update(updateDto);
    }

    private void validateNickname(String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        if (exists) {
            throw new UserException(ErrorStatus.DUPLICATED_NICKNAME);
        }
    }

}
