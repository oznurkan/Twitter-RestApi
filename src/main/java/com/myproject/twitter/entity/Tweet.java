package com.myproject.twitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tweet", schema = "twitter")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tweet içeriği boş bırakılamaz.")
    @Size(max = 255)
    private String content;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull(message = "Kullanıcı alanı boş bırakılamaz")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private Set<Retweet> retweets = new HashSet<>();


    public void addLike(Like like){

        this.likes.add(like);
    }

    public void deleteLike(Like like){

        this.likes.remove(like);
    }

    public void addComment(Comment comment){

        this.comments.add(comment);
    }

    public void deleteComment(Comment comment){

        this.comments.remove(comment);
    }

    public void addRetweet(Retweet retweet){

        this.retweets.add(retweet);
    }

    public void deleteRetweet(Retweet retweet){

        this.retweets.remove(retweet);
    }



}
