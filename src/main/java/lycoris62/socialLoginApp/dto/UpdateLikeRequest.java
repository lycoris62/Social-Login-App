package lycoris62.socialLoginApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLikeRequest {
    private Long postId;
    private Long userId;
}
