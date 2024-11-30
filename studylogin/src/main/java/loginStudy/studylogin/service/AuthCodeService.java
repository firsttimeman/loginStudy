package loginStudy.studylogin.service;

import loginStudy.studylogin.dto.SignUpRequestDto;
import loginStudy.studylogin.exception.AuthCodeException;
import loginStudy.studylogin.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthCodeService {

    private final RedisTemplate<String, Object> redisTemplate;


    public void saveAuthCode(String email, String authCode, SignUpRequestDto data) {
        // 인증 코드 저장
        redisTemplate.opsForValue().set("auth:code:" + email, authCode, 10, TimeUnit.MINUTES);

        // 사용자 데이터 저장
        redisTemplate.opsForValue().set("auth:data:" + email, data, 10, TimeUnit.MINUTES);
    }

    public String getAuthCode(String email) {
        return (String) redisTemplate.opsForValue().get("auth:code:" + email);
    }

    public SignUpRequestDto getAuthData(String email) {
        return (SignUpRequestDto) redisTemplate.opsForValue().get("auth:data:" + email);
    }


    public boolean validateAuthCode(String email, String inputCode) {
        String savedCode = getAuthCode(email);

        if (savedCode == null) {
            throw new AuthCodeException(ErrorCode.AUTH_CODE_NOT_FOUND);
        }
        if (!savedCode.equals(inputCode)) {
            throw new AuthCodeException(ErrorCode.AUTH_CODE_MISMATCH);
        }

        return true;
    }

    public String createAuthCode(String email) {
        String authCode = generateRandomCode(8); // 8자리 인증 코드 생성
        redisTemplate.opsForValue().set("auth:code:" + email, authCode, 10, TimeUnit.MINUTES);
        return authCode;
    }

    public void removeAuthCode(String email) {
        redisTemplate.delete("auth:code:" + email);
        redisTemplate.delete("auth:data:" + email);
    }


    private String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0 -> code.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> code.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> code.append(random.nextInt(10)); // 숫자
            }
        }
        return code.toString();
    }

}
