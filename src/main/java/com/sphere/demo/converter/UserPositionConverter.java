package com.sphere.demo.converter;

import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.mapping.UserPosition;

import java.util.List;
import java.util.stream.Collectors;

public class UserPositionConverter {
    public static List<UserPosition> toUserPositionList(List<Position> positionList) {
        return positionList.stream()
                .map(position -> UserPosition.builder()
                        .position(position)
                        .build())
                .collect(Collectors.toList());
    }
}
