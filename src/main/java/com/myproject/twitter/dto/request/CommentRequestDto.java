package com.myproject.twitter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CommentRequestDto(


        String author,

        @Size(max = 255)
        @NotBlank(message = "Yorum içeriği boş bırakılamaz.")
        String content,

        Long tweetId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
){
}
