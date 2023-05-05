package lycoris62.socialLoginApp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    private Long memberId;
    private String oauthId;
    private String token;
    private String nickname;
    private String profileImageUrl;
}
