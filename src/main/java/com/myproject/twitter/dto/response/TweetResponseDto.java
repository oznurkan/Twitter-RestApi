package com.myproject.twitter.dto.response;

import java.time.LocalDateTime;

public record TweetResponseDto(

        Long tweetId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String nickname,
        Long likeCount,
        Long commentCount,
        Long retweetCount,
        Long bookmarkCount,
        Boolean likedByCurrentUser,
        Boolean retweetedByCurrentUser,
        Boolean bookmarkedByCurrentUser

) {
}
