package lycoris62.socialLoginApp.utils;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    @Test
    void createTokenAndVerifyClaims() {
        JwtUtil jwtUtil = new JwtUtil("kimjaeyunGodBlessJaeyunHiJaeyunNiceToMeetYou");

        Map<String, Object> payloads = Map.of(
                "key11", "value11",
                "key22", "value22");
        Set<Map.Entry<String, Object>> entries = payloads.entrySet();

        String token = jwtUtil.createToken(payloads);
        Map<String, Object> claims = jwtUtil.verifyJWT(token);

        entries.forEach(entry -> assertThat(claims)
                .hasSize(payloads.size() + 2)
                .containsEntry(entry.getKey(), entry.getValue()));
    }
}