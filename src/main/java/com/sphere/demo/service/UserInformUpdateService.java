package com.sphere.demo.service;

import com.sphere.demo.web.dto.UserInformRequestDto;

public interface UserInformUpdateService {
    public void updateUser(UserInformRequestDto.ModifyDto request, Long userId);
}
