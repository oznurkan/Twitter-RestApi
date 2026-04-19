package com.myproject.twitter.controller;

import com.myproject.twitter.dto.request.LikeRequestDto;
import com.myproject.twitter.dto.response.LikeResponseDto;

import com.myproject.twitter.service.LikeService;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping
public class LikeController {

    @Autowired
    private LikeService likeService;


    @PostMapping("/like")
    public LikeResponseDto  likeTweet(@Validated @RequestBody LikeRequestDto likeRequestDto) {
        return likeService.create(likeRequestDto);
    }


    @PostMapping("/dislike")
    public void dislikeTweet(@Validated @RequestBody LikeRequestDto likeRequestDto) {
       likeService.deleteLike(likeRequestDto);
    }


    @GetMapping("/likes")
    public List<LikeResponseDto> getAll(){

        return likeService.findAll();
    }

    @GetMapping("/likes/{id}")
    public LikeResponseDto findById(@Positive @PathVariable("id") Long id){

        return likeService.findById(id);
    }


    @PostMapping("/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponseDto create(@Validated @RequestBody LikeRequestDto likeRequestDto){

        return likeService.create(likeRequestDto);
    }

}
