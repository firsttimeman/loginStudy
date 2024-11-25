package loginStudy.studylogin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import loginStudy.studylogin.type.Gender;
import loginStudy.studylogin.type.Telecom;
import lombok.Getter;

@Entity
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

//    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-XXXX-YYYY이어야 합니다.")
    @Column(nullable = false)
    private String phoneNumber; // 010-1111-2222

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Telecom telecom;

    @Column(nullable = false)
    private boolean isMarketingTrue; // 마케팅 동의여부

    @Column(nullable = false)
    private String password;
}
