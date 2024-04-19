package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public Page<ResponsePostDTO> getAllPosts(@PageableDefault(size=4) final Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    public ResponsePostDTO getPostById(@PathVariable(name = "id") int id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable(name = "id") int id) {
        postService.deletePostById(id);
    }

    @PutMapping("/{id}/pin")
    public ResponsePostDTO pinPostById(@PathVariable(name = "id") int id) {
        return postService.pinPostById(id);
    }

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponsePostDTO createPost(
            @RequestParam("text") String text,
            @RequestParam("cop") String cop,
            @RequestParam("title") String title,
            @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest http) throws IOException {
        return postService.createPost(text, cop, title, image, http);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponsePostDTO editPost(@PathVariable(name = "id") int id,
                                    @RequestParam(value = "text", required = false) String text,
                                    @RequestParam(value = "cop", required = false) String cop,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest http) throws IOException {
        return postService.editPost(id, text, cop, title, image);
    }

    @GetMapping("/CoP/{cop}")
    public Page<ResponsePostDTO> getAllPostsByCop(@PathVariable(name = "cop") String cop, @PageableDefault(size = 4) final Pageable pageable) {
        return postService.getAllPostsByCop(cop, pageable);
    }

    @GetMapping("/search/{contains}")
    public Page<ResponsePostDTO> getPostsContaining(@PathVariable(name = "contains") String contains, @PageableDefault(size=4) final Pageable pageable) {
        return postService.getPostsContaining(contains, pageable);
    }
}
