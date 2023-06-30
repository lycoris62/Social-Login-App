package lycoris62.socialLoginApp.dto;

import lombok.Getter;
import lycoris62.socialLoginApp.domain.Post;

@Getter
public class PostListResponse {

    private final Long id;
    private final String title;
    private final String nickname;
    private final Integer likes;

    public PostListResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likes = post.getLikeUserList().size();
    }
}
