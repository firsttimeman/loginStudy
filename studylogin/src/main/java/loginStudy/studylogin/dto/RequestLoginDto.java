package loginStudy.studylogin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {

    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "비번을 입력해주세요")
    private String password;


}
