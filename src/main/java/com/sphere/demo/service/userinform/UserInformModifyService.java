package com.sphere.demo.service.userinform;

import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.UserInformRequestDto;

public interface UserInformModifyService {
    public void modifyUser(UserInformRequestDto.ModifyDto request, Long userId);
}
