package loginStudy.studylogin.exception;

import lombok.Getter;

@Getter
public class AuthCodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthCodeException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
