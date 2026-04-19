package com.myproject.twitter.dto.response;


import java.time.LocalDateTime;

public record RetweetResponseDto(

        Long id,
        String author,
        Long tweetId,
        LocalDateTime createdAt
) {
}
