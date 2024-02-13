package com.sphere.demo.service.userinform;

import com.sphere.demo.web.dto.UserInformRequestDto;

public interface UserInformUpdateService {
    public void updateUser(UserInformRequestDto.ModifyDto request, Long userId);
}
