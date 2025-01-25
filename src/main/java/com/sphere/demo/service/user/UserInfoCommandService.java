package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.ModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoCommandService{

    private final UserRepository userRepository;

    public void modifyInformUser(ModifyDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        user.update(request.getEmail(), request.getSelfIntroduction());
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
