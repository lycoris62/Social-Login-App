package lycoris62.socialLoginApp.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lycoris62.socialLoginApp.oauth.OAuthInfoResponse;
import lycoris62.socialLoginApp.oauth.OAuthProvider;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
        private String profile_image_url;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getProfileImageUrl() {
        return kakaoAccount.profile.profile_image_url;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
