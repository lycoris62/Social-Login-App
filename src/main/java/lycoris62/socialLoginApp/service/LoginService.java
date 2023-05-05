package lycoris62.socialLoginApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;
import lycoris62.socialLoginApp.oauth.OAuthInfoResponse;
import lycoris62.socialLoginApp.oauth.OAuthLoginParams;
import lycoris62.socialLoginApp.oauth.RequestOAuthInfoService;
import lycoris62.socialLoginApp.repository.MemberRepository;
import lycoris62.socialLoginApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final RequestOAuthInfoService requestOAuthInfoService;
    private final MemberRepository memberRepository;

    public MemberDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        return MemberDto.builder()
                .nickname(oAuthInfoResponse.getName())
                .oauthId(oAuthInfoResponse.getOauthId())
                .profileImageUrl(oAuthInfoResponse.getProfileImageUrl())
                .build();
    }

    public String loginToken(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = getMember(oAuthInfoResponse);
        return JwtUtil.createToken(secretKey, memberRepository.findById(memberId).orElseThrow());
    }

    private Long getMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByOauthId(oAuthInfoResponse.getOauthId()).
                map(MemberDto::getMemberId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member = MemberDto.builder()
                .token(oAuthInfoResponse.getOauthId())
                .nickname(oAuthInfoResponse.getName())
                .profileImageUrl(oAuthInfoResponse.getProfileImageUrl())
                .build();
        Optional<Long> savedMember = memberRepository.save(member);
        return savedMember.orElseThrow();
    }

}
