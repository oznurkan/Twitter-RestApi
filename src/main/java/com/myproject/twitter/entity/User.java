package com.myproject.twitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user", schema = "twitter")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    @NotBlank(message = "Kullanıcı ismi boş bırakılamaz.")
    @Size(max = 150 , min = 3)
    private String nickName;

    @Column(name = "first_name")
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 100)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email alanı boş bırakılamaz.")
    @Size(max = 255 )
    private String email;

    @NotBlank(message = "Şifre alanı boş bırakılamaz")
    @Size(max = 150 )
    private String password;

    @Size(max = 255 )
    @Column(name = "bio")
    private String text;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Tweet> tweets = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Retweet> retweets = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            schema = "twitter",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public void addTweet(Tweet tweet){

        if( !tweets.contains(tweet)){
            this.tweets.add(tweet);
        }

    }

    public void deleteTweet(Tweet tweet){

        this.tweets.remove(tweet);
    }

    public void addComment(Comment comment){

        if( !comments.contains(comment)){
            this.comments.add(comment);
        }

    }

    public void deleteComment(Comment comment){

        this.comments.remove(comment);
    }

    public void addLike(Like like){

        if( !likes.contains(like)){
            this.likes.add(like);
        }

    }

    public void deleteLike(Like like){

        this.likes.remove(like);
    }

    public void addRetweet(Retweet retweet){

        if( !retweets.contains(retweet)){
            this.retweets.add(retweet);
        }

    }

    public void deleteRetweet(Retweet retweet){

        this.retweets.remove(retweet);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
