package lycoris62.socialLoginApp.repository;

import lycoris62.socialLoginApp.domain.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
