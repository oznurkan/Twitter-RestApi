package com.myproject.twitter.util.mapper;

import com.myproject.twitter.dto.request.TweetPatchRequestDto;
import com.myproject.twitter.dto.request.TweetRequestDto;
import com.myproject.twitter.dto.response.TweetResponseDto;
import com.myproject.twitter.entity.Tweet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TweetMapper {

    public TweetResponseDto toResponseDto(Tweet tweet, String currentUserEmail){

        boolean isLikedByMe = tweet.getLikes().stream()
                .anyMatch(like -> like.getUser().getEmail().equals(currentUserEmail));

        boolean isRetweetByMe = tweet.getRetweets().stream()
                .anyMatch(retweet -> retweet.getUser().getEmail().equals(currentUserEmail));

        return new TweetResponseDto(
                tweet.getId(),
                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getUpdatedAt(),
                tweet.getUser() != null ? tweet.getUser().getNickName(): "Unknown User",
                tweet.getLikes().size(),
                tweet.getComments() != null ? tweet.getComments().size() : 0,
                tweet.getRetweets().size(),
                isLikedByMe,
                isRetweetByMe
        );
    }

    public Tweet toEntity(TweetRequestDto tweetRequestDto){

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDto.content());

        if( tweet.getCreatedAt() == null){
            tweet.setCreatedAt(LocalDateTime.now());
        }else{
            tweet.setUpdatedAt(LocalDateTime.now());
        }

        return tweet;
    }

    public void updateEntity(Tweet updateTweet, TweetPatchRequestDto tweetPatchRequestDto){

        if( tweetPatchRequestDto.content() != null){
            updateTweet.setContent(tweetPatchRequestDto.content());
        }


        updateTweet.setUpdatedAt(LocalDateTime.now());
    }
}
