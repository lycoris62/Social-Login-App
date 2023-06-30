package lycoris62.socialLoginApp.repository;

import lycoris62.socialLoginApp.domain.KakaoUser;
import lycoris62.socialLoginApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<KakaoUser> findByToken(String token);
}
