package com.sphere.demo.service.userinform;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.UserInformModifyConverter;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.UserInformRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInformModifyServiceImpl implements UserInformModifyService{
    private final UserRepository userRepository;

    public void modifyUser(UserInformRequestDto.ModifyDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        User modifyUser = UserInformModifyConverter.toUserInform(request, user);
        userRepository.save(modifyUser);
    }
}
