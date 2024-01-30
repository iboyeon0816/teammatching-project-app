package com.sphere.demo.apipayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // COMMON
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다"),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다"),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다"),

    // USER
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자 정보를 찾을 수 없습니다"),
    USER_DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 아이디입니다"),
    USER_DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "USER4003", "이미 존재하는 닉네임입니다"),

    // POSITION
    POSITION_NOT_FOUND(HttpStatus.BAD_REQUEST, "POS4001", "포지션 정보를 찾을 수 없습니다"),

    // TECH STACK
    TECH_STACK_NOT_FOUND(HttpStatus.BAD_REQUEST, "TECH4001", "기술 스택 정보를 찾을 수 없습니다"),

    // PAGE
    PAGE_REQUIRED(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지는 필수 입력값입니다"),
    PAGE_NOT_POSITIVE(HttpStatus.BAD_REQUEST, "PAGE4002", "페이지 값은 양수이어야 합니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
