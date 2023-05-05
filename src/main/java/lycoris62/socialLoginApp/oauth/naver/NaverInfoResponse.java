package lycoris62.socialLoginApp.oauth.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lycoris62.socialLoginApp.oauth.OAuthInfoResponse;
import lycoris62.socialLoginApp.oauth.OAuthProvider;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private long id;
        private String nickname;
        private String profile_image;
    }

    @Override
    public Long getId() {
        return response.id;
    }

    @Override
    public String getName() {
        return response.nickname;
    }

    @Override
    public String getProfileImageUrl() {
        return response.profile_image;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }
}
