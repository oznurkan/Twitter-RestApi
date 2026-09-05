package com.myproject.twitter.dto.response;

public record CommentResponseDto(

        Long commentId,
        Long tweetId,
        String author,
        String content
) {
}
