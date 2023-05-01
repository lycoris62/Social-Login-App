package lycoris62.socialLoginApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    @Value("${kakao.user_id}")
    private String kakaoUserId;
    @Value("${kakao.redirect_url}")
    private String kakaoRedirectUrl;

    @Value("${naver.client_id}")
    private String naverClientId;
    @Value("${naver.redirect_url}")
    private String naverRedirectUrl;
    @Value("${naver.client_secret}")
    private String naverClientSecret;

    public Optional<MemberDto> kakaoLogin(String code) {
        try {
            String accessToken = getKakaoAccessToken(code);
            MemberDto kakaoUserInfo = getKakaoUserInfo(accessToken);
            log.info("userInfo={}", kakaoUserInfo);
            return Optional.ofNullable(kakaoUserInfo);
        } catch (JsonProcessingException e) {
            log.error("카카오 로그인 응답 json 파싱 오류", e);
            return Optional.empty();
        }
    }

    public Optional<MemberDto> naverLogin(String code) {
        try {
            String accessToken = getNaverAccessToken(code);
            MemberDto naverUserInfo = getNaverUserInfo(accessToken);
            log.info("userInfo={}", naverUserInfo);
            return Optional.ofNullable(naverUserInfo);
        } catch (JsonProcessingException e) {
            log.error("카카오 로그인 응답 json 파싱 오류", e);
            return Optional.empty();
        }
    }

    private String getNaverAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", naverClientId);
        body.add("client_secret", naverClientSecret);
        body.add("redirect_uri", naverRedirectUrl);
        body.add("code", code);
        body.add("state", naverClientSecret);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(body, headers);
        JsonNode jsonNode = fetchJsonFromAPI(naverTokenRequest, "https://nid.naver.com/oauth2.0/token");
        log.info("naver token request json = {}", jsonNode);
        return jsonNode.get("access_token").asText();
    }

    private MemberDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
        JsonNode json = fetchJsonFromAPI(naverUserInfoRequest, "https://openapi.naver.com/v1/nid/me");
        log.info("naver response = {}", json);
        JsonNode response = json.get("response");

        return MemberDto.builder()
                .memberId(response.get("id").asLong())
                .nickname(response.get("nickname").asText())
                .profileImageUrl(response.get("profile_image").asText())
                .build();
    }

    private String getKakaoAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoUserId);
        body.add("redirect_uri", kakaoRedirectUrl);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        JsonNode jsonNode = fetchJsonFromAPI(kakaoTokenRequest, "https://kauth.kakao.com/oauth/token");
        return jsonNode.get("access_token").asText();
    }

    private MemberDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        JsonNode json = fetchJsonFromAPI(kakaoUserInfoRequest, "https://kapi.kakao.com/v2/user/me");
        log.info("kakao response = {}", json);

        return MemberDto.builder()
                .memberId(json.get("id").asLong())
                .nickname(json.get("properties").get("nickname").asText())
                .profileImageUrl(json.get("properties").get("profile_image").asText())
                .build();
    }

    private JsonNode fetchJsonFromAPI(HttpEntity<MultiValueMap<String, String>> request, String url) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.getBody());
    }
}
