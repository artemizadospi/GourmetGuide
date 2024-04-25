package com.pweb.gourmetguide.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private byte[] image;
    @Column
    private String title;
    @Column
    private String text;
    @Column(nullable = false)
    private boolean isPinned;
    @ManyToOne(fetch = FetchType.EAGER)
    private User publisher;
    @Column
    private Date publishDate;
    @JsonManagedReference
    @OneToMany(targetEntity = UserLike.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLike> likes;
    @JsonManagedReference
    @OneToMany(targetEntity = UserComment.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserComment> comments;
    @Column
    private String cop;
}
