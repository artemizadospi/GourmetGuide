package com.pweb.gourmetguide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="user_like")
@Data
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int userId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;
        post.getLikes().add(this);
    }
}
