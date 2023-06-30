package lycoris62.socialLoginApp.repository;

import lycoris62.socialLoginApp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
