package com.sphere.demo.service;

public interface UserQueryService {
    boolean isDuplicatedLoginId(String loginId);

    boolean isDuplicatedNickname(String nickname);
}
