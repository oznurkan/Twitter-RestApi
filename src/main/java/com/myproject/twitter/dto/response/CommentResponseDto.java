package com.myproject.twitter.dto.response;

import java.time.LocalDateTime;

public record CommentResponseDto(


        Long commentId,
        String author,
        String content,
        Long tweetId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
