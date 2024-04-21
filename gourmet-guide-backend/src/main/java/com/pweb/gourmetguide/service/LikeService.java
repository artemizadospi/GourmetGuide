package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.exception.PostNotFoundException;
import com.pweb.gourmetguide.model.Post;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.model.UserLike;
import com.pweb.gourmetguide.repository.PostRepository;
import com.pweb.gourmetguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public List<String> getPostLikesById(int id) {
        Post rezPost = postRepository.getPostsById(id);

        if (rezPost == null) {
            throw new PostNotFoundException();
        }

        return rezPost.getLikes().stream()
                .map(like -> userRepository.getUserById(like.getUserId()).getLastName() + " " +
                        userRepository.getUserById(like.getUserId()).getFirstName()).collect(Collectors.toList());
    }

    public ResponsePostDTO likePostById(int id, HttpServletRequest http) throws Exception {
        Post rezPost = postRepository.getPostsById(id);

        if (rezPost == null) {
            throw new PostNotFoundException();
        }

        User user = postService.getLoggedInUser(http.getHeader("Authorization"));
        if (rezPost.getLikes().stream().anyMatch(like -> like.getUserId() == user.getId())) {
            rezPost.getLikes().remove(rezPost.getLikes().stream().filter(like -> like.getUserId() == user.getId()).findFirst().orElseThrow(Exception::new));
        } else {
            UserLike userLike = new UserLike();
            userLike.setUserId(user.getId());
            userLike.setPost(rezPost);
        }

        postRepository.save(rezPost);

        return new ResponsePostDTO(rezPost.getId(), rezPost.getPublisher().getLastName() + " " +
                rezPost.getPublisher().getFirstName(), rezPost.getPublishDate(), rezPost.getTitle(), rezPost.getText(),
                rezPost.isPinned(), rezPost.getLikes(), rezPost.getComments(), rezPost.getCop(), postRepository.count(),
                rezPost.getImage());
    }
}
