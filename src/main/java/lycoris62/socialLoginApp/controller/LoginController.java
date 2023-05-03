package lycoris62.socialLoginApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lycoris62.socialLoginApp.dto.MemberDto;
import lycoris62.socialLoginApp.oauth.kakao.KakaoLoginParams;
import lycoris62.socialLoginApp.oauth.naver.NaverLoginParams;
import lycoris62.socialLoginApp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/login/kakao")
    public String loginKakao(@RequestParam String code, Model model) {
        KakaoLoginParams params = new KakaoLoginParams(code);
        MemberDto member = loginService.login(params);
        model.addAttribute("user", member);
        return "profile";
    }

    @RequestMapping("/login/naver")
    public String loginNaver(@RequestParam String code, Model model) {
        NaverLoginParams params = new NaverLoginParams(code);
        MemberDto member = loginService.login(params);
        model.addAttribute("user", member);
        return "profile";
    }
}
