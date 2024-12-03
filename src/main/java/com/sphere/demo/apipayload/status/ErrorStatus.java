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
    LOGIN_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4004", "존재하지 않는 아이디입니다"),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "USER4004", "비밀번호가 일치하지 않습니다"),
    LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, "USER4005", "아이디나 비밀번호가 일치하지 않습니다"),
    ALREADY_APPLIED_USER(HttpStatus.BAD_REQUEST, "USER4006", "해당 포지션에 대한 신청 정보가 이미 존재합니다"),

    OWN_PROJECT(HttpStatus.BAD_REQUEST, "USER4007", "자신의 프로젝트에는 지원할 수 없습니다"),


    // POSITION
    PROJECT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRO4001", "프로젝트 정보를 찾을 수 없습니다"),
    NOT_RECRUITING_POSITION(HttpStatus.BAD_REQUEST, "PRO4002", "해당 프로젝트가 모집하는 포지션이 아닙니다"),
    ALREADY_MATCHING_END_POSITION(HttpStatus.BAD_REQUEST, "PRO4003", "해당 포지션에 대한 신청이 마감되었습니다"),

    // USER
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "JWT4001", "토큰이 유효하지 않습니다"),

    // POSITION
    POSITION_NOT_FOUND(HttpStatus.BAD_REQUEST, "POS4001", "포지션 정보를 찾을 수 없습니다"),

    // PLATFORM
    PLATFORM_NOT_FOUND(HttpStatus.BAD_REQUEST, "PLAT4001", "플랫폼 정보를 찾을 수 없습니다"),

    // TECH STACK
    TECH_STACK_NOT_FOUND(HttpStatus.BAD_REQUEST, "TECH4001", "기술 스택 정보를 찾을 수 없습니다"),

    // PAGE
    PAGE_REQUIRED(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지는 필수 입력값입니다"),

    PAGE_NOT_POSITIVE(HttpStatus.BAD_REQUEST, "PAGE4002", "페이지 값은 양수이어야 합니다"),

    //community
    COMMUNITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMUNITY4001", "게시글을 찾을 수 없습니다"),

    // UNIV CERT
    EMAIL_EXISTS(HttpStatus.CONFLICT, "UNIV4001", "이미 인증된 이메일입니다. 로그인하세요."),
    INVALID_UNIV_NAME(HttpStatus.BAD_REQUEST, "UNIV4002", "인증이 불가능한 학교명입니다"),
    STUDENT_AUTH_FAILED(HttpStatus.NOT_FOUND, "UNIV4003", "재학생 인증에 실패하였습니다"),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "UNIV4004", "인증 코드가 일치하지 않습니다"),
    USER_ALREADY_AUTHENTICATED(HttpStatus.CONFLICT, "UNIV4005", "이미 인증된 사용자입니다. 회원가입하세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
