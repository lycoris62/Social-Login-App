package lycoris62.socialLoginApp.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private final Key key;
    private final JwtParser jwtParser;

    public JwtUtil(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(Map<String, Object> payloads) {
        Map<String, Object> headers = Map.of(
                "typ", "JWT",
                "alg", "HS256"
        );

        Date ext = new Date();
        long expiredTime = 1000L * 60L * 60L * 2;
        ext.setTime(ext.getTime() + expiredTime);

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(ext)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> verifyJWT(String jwt) {
        return jwtParser.parseClaimsJws(jwt).getBody();
    }
}
