package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.CommentPatchRequestDto;
import com.myproject.twitter.dto.request.CommentRequestDto;
import com.myproject.twitter.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> getAll();
    CommentResponseDto findById(Long id);

    CommentResponseDto replaceOrCreate(Long id, CommentRequestDto commentRequestDto);
    CommentResponseDto update(Long id, CommentPatchRequestDto commentPatchRequestDto);

    CommentResponseDto create(CommentRequestDto commentRequestDto);
    void deleteById(Long id);


    List<CommentResponseDto> searchByContent(String content);




}
