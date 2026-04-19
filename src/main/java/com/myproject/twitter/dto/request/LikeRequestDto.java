package com.myproject.twitter.dto.request;

import java.time.LocalDateTime;

public record LikeRequestDto(

        String author,

        Long tweetId,
        LocalDateTime createdAt
) {
}
