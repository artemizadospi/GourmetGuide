package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{id}/likes")
    public List<String> getPostLikesById(@PathVariable(name = "id") int id) {
        return likeService.getPostLikesById(id);
    }

    @PutMapping("/{id}/like")
    public ResponsePostDTO likePostById(@PathVariable(name = "id") int id, HttpServletRequest http) throws Exception {
        return likeService.likePostById(id, http);
    }
}
