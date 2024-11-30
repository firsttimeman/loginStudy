package loginStudy.studylogin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import loginStudy.studylogin.type.Gender;
import loginStudy.studylogin.type.Telecom;
import loginStudy.studylogin.type.UserRole;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String phoneNumber; // 010-1111-2222

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Telecom telecom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private boolean isMarketingTrue; // 마케팅 동의여부

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;
}
