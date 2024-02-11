package com.gstagram.gstagram.common.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCode {
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다."),
    REGION_CITY_INVALID(HttpStatus.BAD_REQUEST, false, "CITY와 REGION이 적합하지 않습니다."),

    // 401 Unauthorized
    TOKEN_VALIDATION_FAILURE(HttpStatus.UNAUTHORIZED, false, "토큰 검증 실패"),
    REFRESH_TOKEN_VALIDATION_FAILURE(HttpStatus.UNAUTHORIZED, false, "Refresg 토큰 검증 실패"),
    USER_LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, false, "로그인 실패"),

    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없습니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "사용자를 찾을 수 없습니다."),
    CHECK_NOT_FOUND(HttpStatus.NOT_FOUND, false, "체크를 찾을 수 없습니다."),
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 지역을 찾을 수 없습니다."),
    BOOKLET_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 책자를 찾을 수 없습니다."),
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 경로를 찾을 수 없습니다."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 place를 찾을 수 없습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 좋아요를 찾을 수 없습니다."),
    SCRAP_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 스크랩을 찾을 수 없습니다."),

    // 409 Conflict
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, false, "이미 가입한 사용자입니다."),

    //500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버에 오류가 발생하였습니다."),

    // 200 OK
    USER_READ_SUCCESS(HttpStatus.CREATED, true, "사용자 정보 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, true, "사용자 정보 수정 성공"),
    USER_SEARCH_SUCCESS(HttpStatus.OK, true, "사용자 검색 성공"),
    USER_LOGIN_SUCCESS(HttpStatus.OK, true, "사용자 로그인 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, true, "사용자 삭제 성공"),
    BOOKLET_SEARCH_SUCCESS(HttpStatus.OK, true, "책자 검색 성공"),
    BOOKLET_UPDATE_SUCCESS(HttpStatus.OK, true, "책자 수정 성공"),

    TOKEN_REISSUE_SUCCESS(HttpStatus.OK, true, "토큰 재발급 성공"),

    COURSE_UPDATE_SUCCESS(HttpStatus.OK, true, "course 수정 성공"),
    COURSE_ACCESS_SUCCESS(HttpStatus.OK, true, "course 조회 성공"),
    COURSE_DELETED_SUCCESS(HttpStatus.OK, true, "course 삭제 성공"),
    LIKE_READ_SUCCESS(HttpStatus.OK, true, "좋아요 여부 확인 성공"),
    LIKE_DELETED_SUCCESS(HttpStatus.OK, true, "좋아요 삭제 성공"),
    // 201 Created
    USER_CREATE_SUCCESS(HttpStatus.CREATED, true, "사용자 생성 성공"),
    COURSE_CREATED_SUCCESS(HttpStatus.CREATED, true, "경로 게시글 생성 성공"),
    LIKE_CREATED_SUCCESS(HttpStatus.CREATED, true, "좋아요 생성 성공");



    private final HttpStatus httpStatus;
    private final boolean success;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}