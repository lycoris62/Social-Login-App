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
        if (getWhiteUrlList().contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");
        sendExceptionResponse(response, header);
    }

    private void sendExceptionResponse(HttpServletResponse response, String header) throws IOException {
        if (header == null || header.equalsIgnoreCase("")) {
            responseExceptionWithMessage(response, new Exception("Token is null"));
        }
        String token = JwtUtil.getTokenFromHeader(header);
        if (!JwtUtil.isValidToken(secretKey, token)) {
            responseExceptionWithMessage(response, new Exception("Token is invalid"));
        }
        if (JwtUtil.getUserIdFromToken(secretKey, token) == null) {
            responseExceptionWithMessage(response, new Exception("Token isn't memberId"));
        }
    }

    private static List<String> getWhiteUrlList() {
        return Arrays.asList(
                "/api/v1/user/login",
                "/api/v1/test/generateToken",
                "/",
                "/login/kakao",
                "/login/naver",
                "/css/bootstrap.min.css",
                "/image/naver_login_button.png",
                "/image/kakao_login_button.png");
    }

    private void responseExceptionWithMessage(HttpServletResponse response, Exception e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(e.getMessage());
        writer.flush();
        writer.close();
    }
}
