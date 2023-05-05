package lycoris62.socialLoginApp.utils;

import lycoris62.socialLoginApp.dto.MemberDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    @Test
    void createTokenAndVerifyMember() {
        String secretKey = "helloWorldMyNameIsJiGuAndMyFriendIsSunNiceToMeetYou";
        MemberDto member = MemberDto.builder()
                .memberId(1L)
                .nickname("kimjaeyun")
                .profileImageUrl("example.com")
                .build();

        String token = JwtUtil.createToken(secretKey, member);
        Long memberId = JwtUtil.getUserIdFromToken(secretKey, token);

        assertThat(memberId).isEqualTo(member.getMemberId());
    }
}