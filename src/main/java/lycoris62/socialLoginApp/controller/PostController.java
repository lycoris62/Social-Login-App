package lycoris62.socialLoginApp.controller;

import lombok.RequiredArgsConstructor;
import lycoris62.socialLoginApp.domain.Post;
import lycoris62.socialLoginApp.dto.AddPostRequest;
import lycoris62.socialLoginApp.dto.PostListResponse;
import lycoris62.socialLoginApp.dto.PostResponse;
import lycoris62.socialLoginApp.dto.UpdateLikeRequest;
import lycoris62.socialLoginApp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {

        Post post = postService.findById(postId);
        model.addAttribute("post", new PostResponse(post));
        return "post";
    }

    @GetMapping("/post")
    public String getPosts(Model model, UpdateLikeRequest updateLikeRequest) {
        List<PostListResponse> postList = postService.findAll()
                .stream()
                .map(PostListResponse::new)
                .toList();
        model.addAttribute("postList", postList);
        model.addAttribute("updateLikeRequest", updateLikeRequest);

        return "post";
    }

    @GetMapping("/addPostForm")
    public String addPostForm(Model model, AddPostRequest addPostRequest) {
        addPostRequest.setUserId(1L);
        model.addAttribute("addPostRequest", addPostRequest);
        return "addPost";
    }

    @PostMapping("/post")
    public String addPost(@ModelAttribute AddPostRequest request) {
        System.out.println("addPostRequest.getTitle() = " + request.getTitle());
        System.out.println("addPostRequest = " + request.getContent());
        System.out.println("addPostRequest = " + request.getUserId());
        Post savedPost = postService.save(request);
        return "redirect:/post/" + savedPost.getId();
    }

    @PostMapping("/post/like")
    public String updateLike(@RequestBody UpdateLikeRequest request) {
        postService.saveLike(request.getPostId(), request.getUserId());
        return "redirect:/post/" + request.getPostId();
    }
}
