package com.myproject.twitter.dto.request;


import java.time.LocalDateTime;

public record RetweetRequestDto(

        String author,
        Long tweetId,
        LocalDateTime createdAt
) {
}
