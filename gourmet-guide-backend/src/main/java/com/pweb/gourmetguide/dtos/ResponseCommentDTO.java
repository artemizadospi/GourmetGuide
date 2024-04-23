package com.pweb.gourmetguide.dtos;

import com.pweb.gourmetguide.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseCommentDTO {
    private int id;
    private String userName;
    private String text;
    private Date date;
    private String userRole;
    private long totalComments;
}
