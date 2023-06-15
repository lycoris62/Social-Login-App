package lycoris62.socialLoginApp.repository;

import lycoris62.socialLoginApp.dto.MemberDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void save() {

        // given
        MemberDto member = MemberDto.builder()
                .memberId(1L)
                .nickname("kimjaeyun")
                .profileImageUrl("example.com")
                .build();

        // when
        memberRepository.save(member);

        //then
        MemberDto findMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(findMember).isEqualTo(member);
    }
}