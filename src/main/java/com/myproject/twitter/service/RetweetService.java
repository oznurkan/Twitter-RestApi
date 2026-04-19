package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.RetweetRequestDto;
import com.myproject.twitter.dto.response.RetweetResponseDto;

import java.util.List;


public interface RetweetService {

    List<RetweetResponseDto> getAll();
    RetweetResponseDto findById(Long id);

    RetweetResponseDto create(RetweetRequestDto retweetRequestDto);
    void deleteById(Long id);
}
