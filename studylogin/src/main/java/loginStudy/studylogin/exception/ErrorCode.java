package loginStudy.studylogin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST , "MEMBER_NOT_FOUND" ,"계정이 존재하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_EXISTS", "이미 사용중인 이메일입니다"),
    PHONE_NUMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "PHONE_NUMBER_ALREADY_EXISTS", "이미 사용 중인 전화번호입니다."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "INVALID_AUTH_CODE", "인증 코드가 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "비밀번호가 올바르지 않습니다."),
    ACCOUNT_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "ACCOUNT_NOT_ACTIVE", "이메일 인증이 완료되지 않은 계정입니다."),

    MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_SEND_FAILED", "메일 발송 중 오류가 발생했습니다."),

    AUTH_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTH_CODE_NOT_FOUND", "인증 번호가 만료되었거나 존재하지 않습니다."),
    AUTH_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH_CODE_MISMATCH", "인증 번호가 일치하지 않습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
