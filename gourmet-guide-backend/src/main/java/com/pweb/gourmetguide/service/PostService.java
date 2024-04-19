package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.ResponsePostDTO;
import com.pweb.gourmetguide.exception.PostNotFoundException;
import com.pweb.gourmetguide.model.Post;
import com.pweb.gourmetguide.model.User;
import com.pweb.gourmetguide.repository.PostRepository;
import com.pweb.gourmetguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public User getLoggedInUser(String jwt) {
        String[] split_string = jwt.split("\\.");
        String base64EncodedBody = split_string[1];
        String body = new String(Base64.getDecoder().decode(base64EncodedBody));
        JSONObject jsonObject = new JSONObject(body);
        String username = (String) jsonObject.get("username");
        return userRepository.findByUsername(username);
    }

    public List<ResponsePostDTO> getPostDTOsById(int id) {
        return postRepository.findAll().stream()
                .filter(post -> post.getId() == id)
                .map(post -> new ResponsePostDTO(post.getId(), post.getPublisher().getLastName() + " " +
                        post.getPublisher().getFirstName(), post.getPublishDate(), post.getTitle(), post.getText(),
                        post.isPinned(), post.getLikes(), post.getComments(), post.getCop(), postRepository.count(), post.getImage()))
                .collect(Collectors.toList());
    }

    public Page<ResponsePostDTO> getAllPosts(final Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "isPinned", "publishDate");
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Post> postsPage = postRepository.findAll(sortedPageable);
        long count = postRepository.count();
        Page<ResponsePostDTO> responsePostsPage = postsPage.map(post -> new ResponsePostDTO(
                post.getId(),
                post.getPublisher().getLastName() + " " + post.getPublisher().getFirstName(),
                post.getPublishDate(),
                post.getTitle(),
                post.getText(),
                post.isPinned(),
                post.getLikes(),
                post.getComments(),
                post.getCop(),
                count,
                post.getImage()
        ));

        if (responsePostsPage.isEmpty()) {
            throw new PostNotFoundException();
        }

        return responsePostsPage;
    }

    public ResponsePostDTO getPostById(int id) {
        List<ResponsePostDTO> rezPosts = getPostDTOsById(id);
        if (rezPosts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return rezPosts.get(0);
    }

    public void deletePostById(int id) {
        List<ResponsePostDTO> rezPosts = getPostDTOsById(id);
        if (rezPosts.isEmpty()) {
            throw new PostNotFoundException();
        }

        postRepository.deleteById(id);
    }

    public ResponsePostDTO pinPostById(int id) {
        Post rezPost = postRepository.getPostsById(id);

        if (rezPost == null) {
            throw new PostNotFoundException();
        }

        rezPost.setPinned(!rezPost.isPinned());
        postRepository.save(rezPost);

        return new ResponsePostDTO(rezPost.getId(), rezPost.getPublisher().getLastName() + " " +
                rezPost.getPublisher().getFirstName(), rezPost.getPublishDate(), rezPost.getTitle(), rezPost.getText(),
                rezPost.isPinned(), rezPost.getLikes(), rezPost.getComments(), rezPost.getCop(), postRepository.count(), rezPost.getImage());
    }

    public ResponsePostDTO createPost(
            String text,
            String cop,
            String title,
            MultipartFile image, HttpServletRequest http) throws IOException {
        Post createdPost = new Post();
        createdPost.setText(text);
        if (image != null) {
            createdPost.setImage(image.getBytes());
        }
        createdPost.setPublishDate(new Date());
        createdPost.setCop(cop);
        createdPost.setTitle(title);
        createdPost.setLikedByCurrentUser(false);
        User user = getLoggedInUser(http.getHeader("Authorization"));
        createdPost.setPublisher(user);

        postRepository.save(createdPost);

        return new ResponsePostDTO(createdPost.getId(), createdPost.getPublisher().getLastName() + " " +
                createdPost.getPublisher().getFirstName(), createdPost.getPublishDate(), createdPost.getTitle(),
                createdPost.getText(), createdPost.isPinned(), createdPost.getLikes(),
                createdPost.getComments(), createdPost.getCop(), postRepository.count(), createdPost.getImage());
    }

    public ResponsePostDTO editPost(int id,
                                    String text,
                                    String cop,
                                    String title,
                                    MultipartFile image) throws IOException {
        Post rezPost = postRepository.getPostsById(id);
        if (rezPost == null) {
            throw new PostNotFoundException();
        }
        if (text != null && !text.isEmpty()) {
            rezPost.setText(text);
        }
        if (image!= null) {
            rezPost.setImage(image.getBytes());
        }
        if (cop != null && !cop.isEmpty()) {
            rezPost.setCop(cop);
        }
        if (title != null && !title.isEmpty()) {
            rezPost.setTitle(title);
        }
        rezPost.setPublishDate(new Date());

        postRepository.save(rezPost);

        User user = userRepository.getUserById(rezPost.getPublisher().getId());

        return new ResponsePostDTO(rezPost.getId(), user.getLastName() + " " + user.getFirstName(),
                rezPost.getPublishDate(), rezPost.getTitle(), rezPost.getText(), rezPost.isPinned(), rezPost.getLikes(),
                rezPost.getComments(), rezPost.getCop(), postRepository.count(), rezPost.getImage());
    }

    public Page<ResponsePostDTO> getAllPostsByCop(String cop, final Pageable pageable) {
        List<Integer> rezPostsIds = postRepository.findAll().stream()
                .filter(post -> post.getCop().equals(cop))
                .map(Post::getId)
                .collect(Collectors.toList());
        return postRepository.findAllByIdIn(rezPostsIds, pageable)
                .map(post -> new ResponsePostDTO(post.getId(), post.getPublisher().getLastName() + " " +
                        post.getPublisher().getFirstName(), post.getPublishDate(), post.getTitle(), post.getText(),
                        post.isPinned(), post.getLikes(), post.getComments(), post.getCop(), rezPostsIds.size(), post.getImage()));
    }

    public Page<ResponsePostDTO> getPostsContaining(String contains, final Pageable pageable) {
        Page<Post> postsPage = postRepository.findByTitleContaining(contains, pageable);
        Page<ResponsePostDTO> responsePostsPage = postsPage.map(post -> new ResponsePostDTO(
                post.getId(),
                post.getPublisher().getLastName() + " " + post.getPublisher().getFirstName(),
                post.getPublishDate(),
                post.getTitle(),
                post.getText(),
                post.isPinned(),
                post.getLikes(),
                post.getComments(),
                post.getCop(),
                postsPage.getTotalElements(),
                post.getImage()
        ));

        if (responsePostsPage.isEmpty()) {
            throw new PostNotFoundException();
        }

        return responsePostsPage;
    }
}
