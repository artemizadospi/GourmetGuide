package com.pweb.gourmetguide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="user_comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int userId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Date date;
}
