package com.myproject.twitter.service;

import com.myproject.twitter.dto.response.FollowStatusResponseDto;
import com.myproject.twitter.dto.response.FollowUserResponseDto;

import java.util.List;

public interface FollowService {

    FollowStatusResponseDto follow(String currentUserEmail, String targetNickName);

    FollowStatusResponseDto unfollow(String currentUserEmail, String targetNickName);

    List<FollowUserResponseDto> getFollowings(String targetNickName, String currentUserEmail);

    List<FollowUserResponseDto> getFollowers(String targetNickName, String currentUserEmail);

    boolean isFollowing(Long followerId, Long followingId);

    Long countFollowers(Long userId);

    Long countFollowings(Long userId);
}
