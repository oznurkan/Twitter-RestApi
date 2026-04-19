package com.myproject.twitter.util.mapper;

import com.myproject.twitter.dto.request.LikeRequestDto;
import com.myproject.twitter.dto.response.LikeResponseDto;
import com.myproject.twitter.entity.Like;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeMapper {

    public LikeResponseDto toResponseDto(Like like){

        return new LikeResponseDto(
                like.getId(),
                like.getUser() != null ? like.getUser().getNickName() : "Unknown user",
                like.getTweet() != null ? like.getTweet().getId() : 0,
                like.getCreatedAt()
        );

    }

    public Like toEntity(LikeRequestDto likeRequestDto){

        Like like = new Like();

        if( like.getCreatedAt() == null){
            like.setCreatedAt(LocalDateTime.now());
        }

        return like;
    }

}
