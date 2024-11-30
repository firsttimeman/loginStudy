package loginStudy.studylogin.controller;

import jakarta.validation.Valid;
import loginStudy.studylogin.dto.RequestLoginDto;
import loginStudy.studylogin.dto.SignUpRequestDto;
import loginStudy.studylogin.service.AuthCodeService;
import loginStudy.studylogin.service.MailService;
import loginStudy.studylogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private final AuthCodeService authCodeService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        userService.RegisterAccount(requestDto);
        return ResponseEntity.ok("회원가입 요청이 완료되었습니다. 이메일을 확인해주세요.");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(
            @RequestParam("email") String email,
            @RequestParam("authCode") String authCode) {


        userService.activateAccount(email, authCode);
        return ResponseEntity.ok("이메일 인증이 완료되었습니다. 계정이 활성화되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid RequestLoginDto loginDto) {
        String token = userService.login(loginDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body("로그인 성공");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        userService.sendTemporaryPassword(email);
        return ResponseEntity.ok("임시 비밀번호가 발급되었습니다.");
    }

}
