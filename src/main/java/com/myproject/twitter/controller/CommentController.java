package com.myproject.twitter.controller;

import com.myproject.twitter.dto.request.CommentPatchRequestDto;
import com.myproject.twitter.dto.request.CommentRequestDto;
import com.myproject.twitter.dto.response.CommentResponseDto;
import com.myproject.twitter.service.CommentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentResponseDto> getAll(){

        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public CommentResponseDto findById(@Positive @PathVariable("id") Long id){

        return commentService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@Validated @RequestBody CommentRequestDto commentRequestDto){

        return commentService.create(commentRequestDto);
    }

    @PutMapping("/{id}")
    public CommentResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id,@Validated @RequestBody CommentRequestDto commentRequestDto){

        return commentService.replaceOrCreate(id, commentRequestDto);
    }

    @PatchMapping("/{id}")
    public CommentResponseDto update(@Positive @PathVariable("id") Long id,@Validated @RequestBody CommentPatchRequestDto commentPatchRequestDto){

        return commentService.update(id, commentPatchRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id){

        commentService.deleteById(id);
    }


    @GetMapping("/search")
    public List<CommentResponseDto> search(@Size(min = 3, max = 15) @RequestParam("content") String content){
        return commentService.searchByContent(content);
    }

}
