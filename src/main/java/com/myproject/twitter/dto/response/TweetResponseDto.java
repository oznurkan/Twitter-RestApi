package com.myproject.twitter.dto.response;


import java.time.LocalDateTime;


public record TweetResponseDto(


        Long tweetId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String nickname,
        Integer likeCount,
        Integer commentCount,
        Integer retweetCount,
        Boolean likedByCurrentUser,
        Boolean retweetedByCurrentUser

) {
}
