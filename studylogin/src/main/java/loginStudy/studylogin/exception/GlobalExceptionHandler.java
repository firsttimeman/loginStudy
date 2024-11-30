package loginStudy.studylogin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException e) {
        log.error("MemberException 발생 : {}" , e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getCode(),
                e.getErrorCode().getDescription()
        );
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorResponse> handleMailSendException(MailSendException e) {
        log.error("MailSendException 발생 : {}" , e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getCode(),
                e.getErrorCode().getDescription()
        );
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AuthCodeException.class)
    public ResponseEntity<ErrorResponse> handleAuthCodeException(AuthCodeException e) {
        log.error("AuthCodeException 발생: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getCode(),
                e.getErrorCode().getDescription()
        );
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation 오류 발생: {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "입력값 검증 실패: " + errors.toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}
