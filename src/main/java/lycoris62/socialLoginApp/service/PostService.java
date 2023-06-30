package lycoris62.socialLoginApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lycoris62.socialLoginApp.domain.Post;
import lycoris62.socialLoginApp.domain.User;
import lycoris62.socialLoginApp.domain.UserPostLike;
import lycoris62.socialLoginApp.dto.AddPostRequest;
import lycoris62.socialLoginApp.repository.PostRepository;
import lycoris62.socialLoginApp.repository.UserPostLikeRepository;
import lycoris62.socialLoginApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserPostLikeRepository userPostLikeRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("부적절한 postId 입니다."));
    }

    public Post save(AddPostRequest request) {
        return postRepository.save(
                request.toEntity(userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("없는 userId")))
        );
    }

    public boolean saveLike(Long postId, Long userId) {
        if (userPostLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            userPostLikeRepository.deleteByPostIdAndUserId(postId, userId);
            return false;
        } else {
            Post post = postRepository.findById(postId).get();
            User user = userRepository.findById(userId).get();

            UserPostLike userPostLike = UserPostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
            userPostLikeRepository.save(userPostLike);
            return true;
        }
    }
}
