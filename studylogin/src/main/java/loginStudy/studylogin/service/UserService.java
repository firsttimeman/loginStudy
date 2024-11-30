package loginStudy.studylogin.service;

import jakarta.mail.MessagingException;
import loginStudy.studylogin.dto.RequestLoginDto;
import loginStudy.studylogin.dto.SignUpRequestDto;
import loginStudy.studylogin.entity.User;
import loginStudy.studylogin.exception.ErrorCode;
import loginStudy.studylogin.exception.MemberException;
import loginStudy.studylogin.jwt.CustomUserDetails;
import loginStudy.studylogin.jwt.JWTUtil;
import loginStudy.studylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthCodeService authCodeService;
    private final JWTUtil jwtUtil;

    /**
     * 1. 유저 회원가입
     * 2. 유저 중복확인, 전화번호 중복확인 필요
     * 3. 회원가입이 정상적이면 이메일로 계정 활성화 링크를 보내 활성을 해야 로그인 가능
     * <p>
     * <p>
     * 1. 로그인
     * 이메일, 비밀번호로 로그인(JWT 발행)
     * <p>
     * 1. 비밀번호 찾기
     * 이메일로 비밀번호 재설정 페이지 보내거나, 임시 비밀번호 발급
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return new CustomUserDetails(user);
    }

    public void RegisterAccount(SignUpRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new MemberException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new MemberException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodedPassword);


        String authCode = authCodeService.createAuthCode(requestDto.getEmail());
        authCodeService.saveAuthCode(requestDto.getEmail(), authCode, requestDto);

        mailService.sendVerificationCode(requestDto.getEmail(), authCode);


    }

    public void activateAccount(String email, String authCode) {
        if (!authCodeService.validateAuthCode(email, authCode)) {
           throw new MemberException(ErrorCode.INVALID_AUTH_CODE);
        }

        SignUpRequestDto requestDto = authCodeService.getAuthData(email);

        if(requestDto == null) {
            throw new MemberException(ErrorCode.INVALID_AUTH_CODE);
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .birth(requestDto.getBirth())
                .gender(requestDto.getGender())
                .phoneNumber(requestDto.getPhoneNumber())
                .telecom(requestDto.getTelecom())
                .isMarketingTrue(requestDto.isMarketingTrue())
                .userRole(requestDto.getUserRole())
                .isActive(true) // 활성화 상태
                .build();


        userRepository.save(user); // 활성화 상태로 저장

        authCodeService.removeAuthCode(email);
    }

    public String login(RequestLoginDto requestLoginDto) {

        User user = userRepository.findByEmail(requestLoginDto.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(requestLoginDto.getPassword(), user.getPassword())) {
          throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }

        if (!user.isActive()) {
            throw new MemberException(ErrorCode.ACCOUNT_NOT_ACTIVE);
        }

        return jwtUtil.createToken(user.getEmail(), user.getUserRole().name());
    }


    public void sendTemporaryPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        String tempPassword = generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        mailService.sendTemporaryPassword(email, tempPassword);
    }

    private String generateTemporaryPassword() {

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        sb.append((char) (random.nextInt(26) + 65)); // 대문자 1
        sb.append((char) (random.nextInt(26) + 97)); // 소문자 1
        sb.append((char) (random.nextInt(10) + 48)); // 숫자 1
        sb.append((char) (random.nextInt(15) + 33)); // 특수문자 1

        for(int i = 4; i < 8; i++) {
            int type = random.nextInt(3);
            if (type == 0) sb.append((char) (random.nextInt(26) + 65)); // 대문자
            else if (type == 1) sb.append((char) (random.nextInt(26) + 97)); // 소문자
            else sb.append((char) (random.nextInt(10) + 48)); // 숫자
        }

        return sb.toString();
    }


}
