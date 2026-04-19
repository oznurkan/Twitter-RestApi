package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.LikeRequestDto;
import com.myproject.twitter.dto.response.LikeResponseDto;

import java.util.List;

public interface LikeService {

    List<LikeResponseDto> findAll();
    LikeResponseDto findById(Long id);


    LikeResponseDto create(LikeRequestDto likeRequestDto);
    void deleteLike(LikeRequestDto likeRequestDto);


}
