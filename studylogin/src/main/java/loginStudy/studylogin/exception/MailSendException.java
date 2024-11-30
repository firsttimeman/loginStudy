package loginStudy.studylogin.exception;

import lombok.Getter;

@Getter
public class MailSendException extends RuntimeException {

  private final ErrorCode errorCode;

  public MailSendException(ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }

}
