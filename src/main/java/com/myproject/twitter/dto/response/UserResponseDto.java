package com.myproject.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserResponseDto(

        @JsonProperty("nickname")
        String nickName,

        @JsonProperty("firstname")
        String firstName,

        @JsonProperty("lastname")
        String lastName,

        String email,

        @JsonProperty("bio")
        String text,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}
