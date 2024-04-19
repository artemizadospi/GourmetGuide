package com.pweb.gourmetguide.dtos;

import com.pweb.gourmetguide.model.UserComment;
import com.pweb.gourmetguide.model.UserLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponsePostDTO {
    private int id;
    private String publisher;
    public Date publishDate;
    private String title;
    private String text;
    private boolean pinned;
    private List<UserLike> likes;
    private Set<UserComment> comments;
    private String cop;
    private long totalPosts;
    private byte[] image;
}
