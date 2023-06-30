package lycoris62.socialLoginApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.domain.KakaoUser;
import lycoris62.socialLoginApp.domain.User;
import lycoris62.socialLoginApp.dto.MemberDto;
import lycoris62.socialLoginApp.oauth.kakao.KakaoLoginParams;
import lycoris62.socialLoginApp.service.LoginService;
import lycoris62.socialLoginApp.service.UserService;
import lycoris62.socialLoginApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    @Value("${jwt.secret}")
    private String secretKey;
    private final LoginService loginService;
    private final UserService userService;

    @RequestMapping("/login/kakao")
    public String loginKakao(@RequestParam String code) {
        KakaoLoginParams params = new KakaoLoginParams(code);
        Long userId = loginService.login(params).getId();

        return "redirect:/profile/" + userId;
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);
        return "profile";
    }

//    @RequestMapping("/login/naver")
//    public String loginNaver(@RequestParam String code, Model model) {
//        NaverLoginParams params = new NaverLoginParams(code);
//        MemberDto member = loginService.login(params);
//        model.addAttribute("user", member);
//        return "profile";
//    }

    @PostMapping("/api/v1/test/generateToken")
    public ResponseEntity<String> getToken(@RequestBody MemberDto memberDto) {
        log.info("member={}", memberDto);
        String token = JwtUtil.createToken(secretKey, memberDto);
        return ResponseEntity.ok(token);
    }
}
