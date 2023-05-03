package lycoris62.socialLoginApp.oauth.naver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lycoris62.socialLoginApp.oauth.OAuthLoginParams;
import lycoris62.socialLoginApp.oauth.OAuthProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);

        return body;
    }
}
