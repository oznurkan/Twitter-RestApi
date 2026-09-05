package com.myproject.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserProfileResponseDto(

        @JsonProperty("nickname")
        String nickName,

        @JsonProperty("firstname")
        String firstName,

        @JsonProperty("lastname")
        String lastName,

        String email,

        String bio,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long tweetCount,

        Long followerCount,

        Long followingCount,

        Boolean followedByCurrentUser


) {
}
