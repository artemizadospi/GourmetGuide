package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.ResponseCommentDTO;
import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public Page<ResponseCommentDTO> getPostCommentsById(@PathVariable(name = "id") int id, @PageableDefault(size=3) final Pageable pageable) {
        return commentService.getPostCommentsById(id, pageable);
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public void deleteCommentById(@PathVariable(name = "postId") int postId,
                                  @PathVariable(name = "commentId") int commentId, HttpServletRequest http) {
        commentService.deleteCommentById(postId, commentId, http);
    }

    @PutMapping("{postId}/comments/{commentId}")
    public ResponseCommentDTO updateCommentById(@PathVariable(name = "postId") int postId, @PathVariable(name = "commentId") int commentId, @RequestBody String text, HttpServletRequest http) {
        return commentService.updateCommentById(postId, commentId, text, http);
    }

    @PutMapping("/{id}/comment")
    public ResponsePostDTO commentOnPostById(@PathVariable(name = "id") int id, @RequestBody String comment, HttpServletRequest http) {
        return commentService.commentOnPostById(id, comment, http);
    }
}
