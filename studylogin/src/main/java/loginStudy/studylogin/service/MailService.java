package loginStudy.studylogin.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import loginStudy.studylogin.exception.ErrorCode;
import loginStudy.studylogin.exception.MailSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String SENDER_EMAIL = "sangwha0@gmail.com";



    public MimeMessage createMail(String recipientEmail, String authCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(SENDER_EMAIL);
        message.setRecipients(MimeMessage.RecipientType.TO, recipientEmail);
        message.setSubject("회원가입 이메일 인증");
        String body = String.format(
                """
                <h3>회원가입 인증 코드입니다.</h3>
                <h1>%s</h1>
                <p>코드를 입력해 회원가입을 완료하세요.</p>
                """,
                authCode
        );
        message.setText(body, "UTF-8", "html");
        return message;
    }


    public void sendVerificationCode(String email, String authCode) {
        try {
            MimeMessage message = createMail(email, authCode);
            mailSender.send(message); // 이메일 발송
        } catch (MailException | MessagingException e) {
            throw new MailSendException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    public MimeMessage createTemporaryPasswordMail(String email, String tempPassword) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(SENDER_EMAIL);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("임시 비밀번호 발급 안내");
        String body = String.format(
                """
               
               <h3> 임시 비밀번호가 발급되었습니다.</h3>
               <h1>%s</h1>
               <p> 임시 비밀번호를 발급해드렸습니다.</p>
                """,
                tempPassword
        );
        message.setText(body, "UTF-8", "html");
        return message;

    }

    public void sendTemporaryPassword(String email, String tempPassword) {
        try {
            MimeMessage message = createTemporaryPasswordMail(email, tempPassword);
            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new MailSendException(ErrorCode.MAIL_SEND_FAILED);
        }
    }



}
