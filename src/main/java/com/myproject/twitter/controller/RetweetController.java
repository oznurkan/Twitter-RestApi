package com.myproject.twitter.controller;

import com.myproject.twitter.dto.request.RetweetRequestDto;

import com.myproject.twitter.dto.response.RetweetResponseDto;
import com.myproject.twitter.service.RetweetService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/retweets")
public class RetweetController {

    @Autowired
    private RetweetService retweetService;

    @GetMapping
    public List<RetweetResponseDto> getAll(){

        return retweetService.getAll();
    }

    @GetMapping("/{id}")
    public RetweetResponseDto findById(@Positive @PathVariable("id") Long id){

        return retweetService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetweetResponseDto create(@Validated @RequestBody RetweetRequestDto retweetRequestDto){

        return retweetService.create(retweetRequestDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id){

        retweetService.deleteById(id);
    }
}
