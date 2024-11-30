package loginStudy.studylogin.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    ADMIN("ADMIN"), USER("USER");

    private final String description;
}
