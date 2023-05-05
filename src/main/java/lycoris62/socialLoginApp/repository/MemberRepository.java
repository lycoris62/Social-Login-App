package lycoris62.socialLoginApp.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepository {

    private final Map<Long, MemberDto> store = new HashMap<>();
    private long sequence = 0L;

    @PostConstruct
    private void createExample() {
        store.put(sequence, MemberDto.builder().memberId(sequence++).oauthId("").nickname("member0").profileImageUrl("example0.com").build());
        store.put(sequence, MemberDto.builder().memberId(sequence++).oauthId("").nickname("member1").profileImageUrl("example1.com").build());
        store.put(sequence, MemberDto.builder().memberId(sequence++).oauthId("").nickname("member2").profileImageUrl("example2.com").build());
    }

    public Optional<MemberDto> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }

    public Optional<MemberDto> findByOauthId(String oauthId) {
        for (MemberDto memberDto : store.values()) {
            if (memberDto.getOauthId() != null && memberDto.getOauthId().equals(oauthId)) return Optional.of(memberDto);
        }
        return Optional.empty();
    }

    public Optional<List<MemberDto>> findAll() {
        return Optional.of(store.values().stream().toList());
    }

    public Optional<Long> save(MemberDto memberDto) {
        memberDto.setMemberId(sequence++);
        store.put(memberDto.getMemberId(), memberDto);
        return memberDto.getMemberId().describeConstable();
    }

}
