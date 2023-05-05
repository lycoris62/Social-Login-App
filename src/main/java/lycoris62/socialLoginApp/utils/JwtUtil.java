package lycoris62.socialLoginApp.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

    public static String createToken(String secretKey, MemberDto memberDto) {
        log.info("staticKey={}", createSignature(secretKey));
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberDto))
                .setSubject(String.valueOf(memberDto.getMemberId()))
                .setExpiration(createExpiredDate())
                .signWith(createSignature(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isValidToken(String secretKey, String token) {
        try {
            Claims claims = getClaimsFromToken(secretKey, token);
            log.info("expiredTime={}", claims.getExpiration());
            log.info("memberId={}", claims.get("memberId"));
            log.info("nickname={}", claims.get("nickname"));
            log.info("profileImageUrl={}", claims.get("profileImageUrl"));
            log.info("subject={}", claims.getSubject());

            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired", e);
            return false;
        } catch (JwtException e) {
            log.error("Token Tampered", e);
            return false;
        } catch (NullPointerException e) {
            log.error("Token is null", e);
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public static Long getUserIdFromToken(String secretKey, String token) {
        Claims claims = getClaimsFromToken(secretKey, token);
        return Long.valueOf(claims.get("memberId").toString());
    }

    private static Map<String, Object> createHeader() {
        return Map.of(
                "typ", "JWT",
                "alg", "HS256",
                "regDate", System.currentTimeMillis()
        );
    }

    private static Date createExpiredDate() {
        Date ext = new Date();
        long expiredTime = 1000L * 60L * 60L * 2L;
        ext.setTime(ext.getTime() + expiredTime);
        return ext;
    }

    private static Map<String, Object> createClaims(MemberDto memberDto) {
        return Map.of(
                "memberId", memberDto.getMemberId(),
                "nickname", memberDto.getNickname(),
                "profileImageUrl", memberDto.getProfileImageUrl()
        );
    }

    private static Key createSignature(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private static Claims getClaimsFromToken(String secretKey, String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(createSignature(secretKey)).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
