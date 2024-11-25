package loginStudy.studylogin.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Telecom {

    SKT("SKT 텔레콩"),
    KT("KT"),
    LGU("LG 유플러스");


    private final String description;
}
