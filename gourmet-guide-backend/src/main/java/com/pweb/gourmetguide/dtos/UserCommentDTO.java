package com.pweb.gourmetguide.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCommentDTO {
    private int id;
    private int userId;
    private int postId;
    private String text;
    private Date date;
}
