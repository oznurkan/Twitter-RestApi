package com.myproject.twitter.dto.response;



import java.time.LocalDateTime;

public record LikeResponseDto(

    Long likeId,

    String author,

    Long tweetId,

    LocalDateTime createdAt
) {
}
