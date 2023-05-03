package lycoris62.socialLoginApp.oauth;

public interface OAuthInfoResponse {
    String getName();
    String getProfileImageUrl();
    OAuthProvider getOAuthProvider();
}
