package com.myproject.twitter.controller;

import com.myproject.twitter.dto.response.FollowStatusResponseDto;
import com.myproject.twitter.dto.response.FollowUserResponseDto;
import com.myproject.twitter.service.FollowService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users/{nickName}")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    @ResponseStatus(HttpStatus.CREATED)
    public FollowStatusResponseDto follow(
            @NotBlank(message = "Kullanıcı adı boş bırakılamaz.") @PathVariable String nickName,
            @AuthenticationPrincipal UserDetails userDetails) {

        return followService.follow(userDetails.getUsername(), nickName);
    }

    @DeleteMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public FollowStatusResponseDto unfollow(
            @NotBlank(message = "Kullanıcı adı boş bırakılamaz.") @PathVariable String nickName,
            @AuthenticationPrincipal UserDetails userDetails) {

        return followService.unfollow(userDetails.getUsername(), nickName);
    }

    @GetMapping("/followings")
    public List<FollowUserResponseDto> getFollowings(
            @NotBlank(message = "Kullanıcı adı boş bırakılamaz.") @PathVariable String nickName,
            @AuthenticationPrincipal UserDetails userDetails) {

        return followService.getFollowings(nickName, userDetails.getUsername());
    }

    @GetMapping("/followers")
    public List<FollowUserResponseDto> getFollowers(
            @NotBlank(message = "Kullanıcı adı boş bırakılamaz.") @PathVariable String nickName,
            @AuthenticationPrincipal UserDetails userDetails) {

        return followService.getFollowers(nickName, userDetails.getUsername());
    }
}
