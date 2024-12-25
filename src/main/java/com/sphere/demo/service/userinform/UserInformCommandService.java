package com.sphere.demo.service.userinform;

import com.sphere.demo.web.dto.UserInformRequestDto;

public interface UserInformCommandService {
    void modifyInformUser(UserInformRequestDto.ModifyDto request, Long userId);

    void deleteUser(Long userId);
}
