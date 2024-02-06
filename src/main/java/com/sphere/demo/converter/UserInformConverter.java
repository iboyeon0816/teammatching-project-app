package com.sphere.demo.converter;

import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import com.sphere.demo.web.dto.UserResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserInformConverter {
    public static UserResponseDto.InformResultDto toInformResultDto(User user, List<UserPosition> positions, List<UserTechStack> techStacks){
        List<Long> positionIdList = positions.stream()
                .map(UserPosition::getPosition)
                .map(Position::getId)
                .collect(Collectors.toList());

        List<Long> teckStackIdList = techStacks.stream()
                .map(UserTechStack::getTechnologyStack)
                .map(TechnologyStack::getId)
                .collect(Collectors.toList());

        return UserResponseDto.InformResultDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .school(user.getSchool())
                .major(user.getMajor())
                .positionIdList(positionIdList)
                .techStackIdList(teckStackIdList)
//                .portfolio(new ArrayList<>())
                .build();
    }

    public static UserResponseDto.InformResultDto toResultModifyDto(User user, List<UserPosition> positions, List<UserTechStack> techStacks){
        List<Long> positionIdList = positions.stream()
                .map(UserPosition::getPosition)
                .map(Position::getId)
                .collect(Collectors.toList());

        List<Long> teckStackIdList = techStacks.stream()
                .map(UserTechStack::getTechnologyStack)
                .map(TechnologyStack::getId)
                .collect(Collectors.toList());

        return UserResponseDto.InformResultDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .positionIdList(positionIdList)
                .techStackIdList(teckStackIdList)
//                .portfolio(new ArrayList<>())
                .build();
    }
}
