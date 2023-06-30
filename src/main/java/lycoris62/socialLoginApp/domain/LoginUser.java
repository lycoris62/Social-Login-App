package lycoris62.socialLoginApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("LOGIN")
public class LoginUser extends User {

    @Column(name = "login_id", nullable = false, unique = true, length = 30)
    private String loginId;

    @Column(name = "password", nullable = false, length = 80)
    private String password;

    public LoginUser(String nickname, String profileImageUrl, String loginId, String password) {
        super(AuthType.LOGIN, nickname, profileImageUrl);
        this.loginId = loginId;
        this.password = password;
    }
}
