package com.myproject.twitter.util.mapper;

import com.myproject.twitter.dto.request.RetweetRequestDto;
import com.myproject.twitter.dto.response.RetweetResponseDto;

import com.myproject.twitter.entity.Retweet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RetweetMapper {

    public RetweetResponseDto toResponseDto(Retweet retweet){

        return new RetweetResponseDto(
                retweet.getId(),
                retweet.getUser() != null ? retweet.getUser().getNickName() : "unknown user",
                retweet.getTweet() != null ? retweet.getTweet().getId() : 0,
                retweet.getCreatedAt()
        );

    }

    public Retweet toEntity(RetweetRequestDto retweetRequestDto){

        Retweet retweet = new Retweet();

        if( retweet.getCreatedAt() == null){
            retweet.setCreatedAt(LocalDateTime.now());
        }

        return retweet;
    }




}
