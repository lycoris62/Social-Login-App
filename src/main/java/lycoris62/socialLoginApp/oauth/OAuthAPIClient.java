package lycoris62.socialLoginApp.oauth;

public interface OAuthAPIClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
