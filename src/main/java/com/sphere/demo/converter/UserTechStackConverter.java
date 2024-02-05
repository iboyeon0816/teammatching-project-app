package com.sphere.demo.converter;

import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.mapping.UserTechStack;

import java.util.List;
import java.util.stream.Collectors;

public class UserTechStackConverter {
    public static List<UserTechStack> toUserTechStack(List<TechnologyStack> technologyStackList) {
        return technologyStackList.stream()
                .map(techStack -> UserTechStack.builder()
                        .technologyStack(techStack)
                        .build())
                .collect(Collectors.toList());
    }
}
