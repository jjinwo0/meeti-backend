package yjhb.meeti.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "business-exception-test"),


    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 access token이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),
    NOT_EQUAL_PASSWORD(HttpStatus.UNAUTHORIZED, "A-009", "Password가 일치하지 않습니다."),
    NOT_EQUAL_CODE(HttpStatus.UNAUTHORIZED, "A-010", "Email Code가 일치하지 않습니다."),
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "A-011", "해당 회원에게 접근 권한이 없습니다."),

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입입니다. (memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원입니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),
    NOT_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "M-004", "일치하는 Email 정보가 존재하지 않습니다."),
    NOT_EXISTS_PASSWORD(HttpStatus.BAD_REQUEST, "M-005", "Password가 일치하지 않습니다."),
    USERNAME_IS_NULL(HttpStatus.BAD_REQUEST, "M-006", "Username이 null 입니다."),
    NOT_VALID_CALENDAR(HttpStatus.BAD_REQUEST, "M-007", "해당 회원에게 접근 권한이 없는 일정입니다."),

    // 스케쥴
    NOT_FOUND_CALENDER(HttpStatus.NOT_FOUND, "S-001", "스케쥴이 존재하지 않습니다."),

    // 오피스
    NOT_FOUND_OFFICE(HttpStatus.NOT_FOUND, "O-001","오피스가 존재하지 않습니다."),

    // 승인
    NOT_FOUND_APPROVAL(HttpStatus.NOT_FOUND, "A-001", "결재 요청 내역이 존재하지 않습니다."),

    // 예약
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "R-001", "예약 내역이 존재하지 않습니다."),
    ALREADY_EXISTS_RESERVATION(HttpStatus.UNAUTHORIZED, "R-002", "이미 예약된 공간입니다."),

    // 파일
    NOT_FOUND_FILE(HttpStatus.UNAUTHORIZED, "F-001", "해당 파일을 찾을 수 없습니다."),

    // 결제
    INVALID_PAYMENT(HttpStatus.BAD_REQUEST, "P-001", "유효하지 않은 결제 요청입니다."),
    PAY_FAILED(HttpStatus.FORBIDDEN, "P-002", "결제 실패."),

    // 회의록
    NOT_FOUND_MEETING(HttpStatus.NOT_FOUND, "M-001", "해당하는 회의록을 찾을 수 업습니다."),

    // 파일
    FILE_CONVERT_FAIL(HttpStatus.BAD_REQUEST, "F-001", "MultipartFile -> File 전환 실패"),

    // 친구
    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "FR-001", "해당하는 친구 정보 객체를 찾을 수 없습니다."),
    FRIEND_ALREADY_PERMIT(HttpStatus.BAD_REQUEST, "FR-002", "이미 친구 관계입니다."),
    FRIEND_NOT_PERMIT(HttpStatus.BAD_REQUEST, "FR-003", "친구 요청 수락이 완료되지 않았습니다."),
    ALREADY_REQUEST(HttpStatus.BAD_REQUEST, "FR-004", "이미 친구 요청한 회원입니다."),

    // 채팅
    NOT_FOUNT_ROOM(HttpStatus.BAD_REQUEST, "C-001", "ChatRoom을 찾을 수 없습니다."),
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
