package com.myproject.twitter.repository;

import com.myproject.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("""
        SELECT t FROM Tweet t WHERE LOWER(t.content) LIKE LOWER(CONCAT('%', :text, '%'))
    """)
    List<Tweet> searchTweetByContext(@Param("text") String text);

    List<Tweet> findByUserId(@Param("userId") Long userId);

    List<Tweet> findAllByOrderByCreatedAtDesc();



}
