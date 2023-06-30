package lycoris62.socialLoginApp.controller;

import lombok.RequiredArgsConstructor;
import lycoris62.socialLoginApp.domain.Post;
import lycoris62.socialLoginApp.dto.PostListResponse;
import lycoris62.socialLoginApp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {

        List<PostListResponse> postList = postService.findAll()
                .stream()
                .map(PostListResponse::new)
                .toList();
        model.addAttribute("postList", postList);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
