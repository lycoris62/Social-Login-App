package lycoris62.socialLoginApp.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lycoris62.socialLoginApp.domain.*;
import lycoris62.socialLoginApp.repository.PostRepository;
import lycoris62.socialLoginApp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public User findByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 userId"));
    }

    @PostConstruct
    public void addDummyData() {
        add();
    }

    @Transactional
    public void add() {
        KakaoUser kakaoUser = KakaoUser.builder()
                .profileImageUrl("www.example.com/image/1")
                .nickname("kjy01")
                .token("token01")
                .build();
        userRepository.save(kakaoUser);

        userRepository.save(LoginUser.builder()
                .profileImageUrl("www.example.com/image/2")
                .nickname("kjy02")
                .loginId("lycoris62")
                .password("q1w2e3r4")
                .build());

        postRepository.save(Post.builder()
                .title("title01")
                .content("content01")
                .user(userRepository.findById(1L).get())
                .build());

        postRepository.save(Post.builder()
                .title("title02")
                .content("content02")
                .user(userRepository.findById(2L).get())
                .build());
    }
}
