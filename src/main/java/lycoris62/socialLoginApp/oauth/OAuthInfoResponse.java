package lycoris62.socialLoginApp.oauth;

public interface OAuthInfoResponse {
    Long getId();
    String getName();
    String getProfileImageUrl();
    OAuthProvider getOAuthProvider();
}
