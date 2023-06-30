package lycoris62.socialLoginApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("KAKAO")
public class KakaoUser extends User {

    @Column(name = "token", nullable = false)
    private String token;

    public KakaoUser(String nickname, String profileImageUrl, String token) {
        super(AuthType.KAKAO, nickname, profileImageUrl);
        this.token = token;
    }
}
