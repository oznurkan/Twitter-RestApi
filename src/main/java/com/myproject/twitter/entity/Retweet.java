package com.myproject.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "retweet", schema = "twitter")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Retweet işlemi için kullanıcı olmalıdır")
    private User user;


    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @NotNull(message = "Retweet işlemi için tweet olmalıdır")
    private Tweet tweet;
}
