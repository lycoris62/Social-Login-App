package lycoris62.socialLoginApp.dto;

import lombok.Getter;
import lycoris62.socialLoginApp.domain.Post;
import lycoris62.socialLoginApp.domain.User;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final User user;
    private final Integer likes;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser();
        this.likes = post.getLikeUserList().size();
    }
}
