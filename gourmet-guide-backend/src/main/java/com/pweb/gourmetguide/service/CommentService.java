package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.ResponseCommentDTO;
import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.dtos.UserCommentDTO;
import com.pweb.gourmetguide.exception.CommentNotFoundException;
import com.pweb.gourmetguide.exception.PostNotFoundException;
import com.pweb.gourmetguide.model.Post;
import com.pweb.gourmetguide.model.UserComment;
import com.pweb.gourmetguide.repository.CommentRepository;
import com.pweb.gourmetguide.repository.PostRepository;
import com.pweb.gourmetguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.pweb.gourmetguide.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;

    public Page<ResponseCommentDTO> getPostCommentsById(int id, final Pageable pageable) {
        Post rezPost = postRepository.getPostsById(id);

        if (rezPost == null) {
            throw new PostNotFoundException();
        }

        Page<UserComment> commentsPage = commentRepository.getAllCommentsByPostId(id, pageable);

        return commentsPage.map(comments -> new ResponseCommentDTO(
                userRepository.getUserById(comments.getUserId()).getLastName() + " " +
                        userRepository.getUserById(comments.getUserId()).getFirstName(),
                comments.getText(), comments.getDate(), userRepository.getUserById(comments.getUserId()).getRole().toString(),
                commentsPage.getTotalElements()
        ));
    }

    public void deleteCommentById(int postId, int commentId, HttpServletRequest http) {
        Post rezPost = postRepository.getPostsById(postId);
        if (rezPost == null) {
            throw new PostNotFoundException();
        }
        Optional<UserComment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException();
        }
        User currentUser = userRepository.getUserById(comment.get().getUserId());
        User loggedInUser = postService.getLoggedInUser(http.getHeader("Authorization"));
        if (currentUser.getId() == loggedInUser.getId()) {
            rezPost.getComments().remove(comment.get());
            commentRepository.deleteById(commentId);
        }
    }

    public UserCommentDTO updateCommentById(int postId, int commentId, String text, HttpServletRequest http) {
        Post rezPost = postRepository.getPostsById(postId);
        if (rezPost == null) {
            throw new PostNotFoundException();
        }
        Optional<UserComment> rezComment = commentRepository.findById(commentId);
        if (rezComment.isEmpty()) {
            throw new CommentNotFoundException();
        }
        UserComment commentValue = rezComment.get();
        if (!text.isEmpty())
            commentValue.setText(text);
        commentRepository.save(commentValue);
        return new UserCommentDTO(commentValue.getId(), commentValue.getUserId(), commentValue.getPost().getId(),
                commentValue.getText(), commentValue.getDate());
    }

    public ResponsePostDTO commentOnPostById(int id, String comment, HttpServletRequest http) {
        Post rezPost = postRepository.getPostsById(id);

        if (rezPost == null) {
            throw new PostNotFoundException();
        }

        User user = postService.getLoggedInUser(http.getHeader("Authorization"));
        UserComment userComment = new UserComment();
        userComment.setUserId(user.getId());
        userComment.setPost(rezPost);
        userComment.setText(comment);
        userComment.setDate(new Date());

        rezPost.getComments().add(userComment);

        postRepository.save(rezPost);

        return new ResponsePostDTO(rezPost.getId(), rezPost.getPublisher().getLastName() + " " +
                rezPost.getPublisher().getFirstName(), rezPost.getPublishDate(), rezPost.getTitle(), rezPost.getText(),
                rezPost.isPinned(), rezPost.getLikes(), rezPost.getComments(), rezPost.getCop(), postRepository.count(), rezPost.getImage());
    }
}
