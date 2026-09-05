package com.myproject.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FollowUserResponseDto(

        @JsonProperty("nickname")
        String nickName,

        @JsonProperty("firstname")
        String firstName,

        @JsonProperty("lastname")
        String lastName,

        @JsonProperty("bio")
        String bio,

        @JsonProperty("followedByCurrentUser")
        Boolean followedByCurrentUser
) {
}
