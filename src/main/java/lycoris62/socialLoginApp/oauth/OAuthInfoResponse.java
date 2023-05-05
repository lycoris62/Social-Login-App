package lycoris62.socialLoginApp.oauth;

public interface OAuthInfoResponse {
    String getOauthId();
    String getName();
    String getProfileImageUrl();
    OAuthProvider getOAuthProvider();
}
