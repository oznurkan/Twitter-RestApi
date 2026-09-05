package com.myproject.twitter.util.mapper;

import com.myproject.twitter.dto.response.FollowUserResponseDto;
import com.myproject.twitter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    public FollowUserResponseDto toFollowUserResponseDto(User user, boolean followedByCurrentUser) {
        return new FollowUserResponseDto(
                user.getNickName(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                followedByCurrentUser
        );
    }
}