package com.myproject.twitter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CommentPatchRequestDto(


        //@NotBlank(message = "Yorum güncellemesi için kullanıcı alanı boş bırakılamaz.")
        String author,

        @Size(max = 255)
        @NotBlank(message = "Yorum içeriği boş bırakılamaz.")
        String content,

        @NotNull(message = "Tweet ID boş olamaz!")
        Long tweetId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
