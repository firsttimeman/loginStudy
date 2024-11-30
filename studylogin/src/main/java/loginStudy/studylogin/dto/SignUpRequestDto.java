package loginStudy.studylogin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import loginStudy.studylogin.entity.User;
import loginStudy.studylogin.type.Gender;
import loginStudy.studylogin.type.Telecom;
import loginStudy.studylogin.type.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Email(message = "올바른 이메일 형식이어야 합니다")
    @NotBlank(message = "이메일은 필수적으로 입력해야합니다")
    private String email;

    @NotBlank(message = "생년월일을 필수입력사항입니다")
    @Pattern(regexp = "\\d{8}", message = "생년월일은 8자리 숫자여야 합니다.")
    private String birth;


    private Gender gender;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-XXXX-YYYY이어야 합니다.")
    private String phoneNumber;

    private Telecom telecom;

    @JsonProperty("isMarketingTrue")
    private boolean isMarketingTrue;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w]).{8,}$",
            message = "비밀번호는 대소문자, 특수문자, 숫자를 포함한 8자리 이상이어야 합니다."
    )

    private String password;

    @NotNull(message = "권한은 입력 필수 사항 입니다.")
    private UserRole userRole;

    public static SignUpRequestDto from(User user) {
        return SignUpRequestDto.builder()
                .email(user.getEmail())
                .birth(user.getBirth())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .telecom(user.getTelecom())
                .isMarketingTrue(user.isMarketingTrue())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .build();
    }



}
