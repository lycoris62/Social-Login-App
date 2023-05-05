package lycoris62.socialLoginApp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.utils.JwtUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 토큰이 필요하지 않은 API URL 구성
        List<String> list = Arrays.asList(
                "/api/v1/user/login",
                "/api/v1/test/generateToken",
                "/",
                "/login/kakao",
                "/login/naver",
                "/css/bootstrap.min.css",
                "/image/naver_login_button.png",
                "/image/kakao_login_button.png");

        log.info("req uri={}", request.getRequestURI());

        // 토큰이 필요하지 않은 API URL 로직 처리 없이 다음 필터로 이동
        if (list.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 클라에서 API 요청할 떄 Header 확인
        String header = request.getHeader("Authorization");
        log.info("header={}", header);
        log.info("secretKey={}", secretKey);

        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = JwtUtil.getTokenFromHeader(header);
                log.info("token={}", token);

                if (JwtUtil.isValidToken(secretKey, token)) {
                    Long memberId = JwtUtil.getUserIdFromToken(secretKey, token);
                    log.info("memberId={}", memberId);

                    if (memberId != null) {
                        filterChain.doFilter(request, response);
                    } else {
                        throw new Exception("Token isn't memberId");
                    }
                } else {
                    throw new Exception("Token is invalid");
                }
            } else {
                throw new Exception("Token is null");
            }
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.print(e.getMessage());
            writer.flush();
            writer.close();
        }
    }
}
