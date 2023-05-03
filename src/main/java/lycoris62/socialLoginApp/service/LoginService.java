package lycoris62.socialLoginApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;
import lycoris62.socialLoginApp.oauth.OAuthInfoResponse;
import lycoris62.socialLoginApp.oauth.OAuthLoginParams;
import lycoris62.socialLoginApp.oauth.RequestOAuthInfoService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final RequestOAuthInfoService requestOAuthInfoService;

    public MemberDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        return MemberDto.builder()
                .nickname(oAuthInfoResponse.getName())
                .profileImageUrl(oAuthInfoResponse.getProfileImageUrl())
                .build();
    }
}
