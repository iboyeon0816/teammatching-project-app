package com.sphere.demo.service;


import com.sphere.demo.web.dto.UserRequestDto.JoinDto;

public interface UserCommandService {
    void join(JoinDto joinDto);
}
