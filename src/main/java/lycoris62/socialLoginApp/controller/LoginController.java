package lycoris62.socialLoginApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/")
    public String home(@RequestParam String name, Model model) {
        if (name == null) {
            name = "java";
        }
        model.addAttribute("name", name);
        return "index";
    }
}
