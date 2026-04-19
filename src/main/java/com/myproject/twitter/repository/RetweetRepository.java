package com.myproject.twitter.repository;

import com.myproject.twitter.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);

    Optional<Retweet> findByUserIdAndTweetId(Long userId, Long tweetId);
}
