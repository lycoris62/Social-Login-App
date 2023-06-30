package lycoris62.socialLoginApp.dto;

import lombok.Getter;
import lombok.Setter;
import lycoris62.socialLoginApp.domain.Post;
import lycoris62.socialLoginApp.domain.User;

@Getter
@Setter
public class AddPostRequest {

    private String title;
    private String content;
    private Long userId;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
