package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.TweetPatchRequestDto;
import com.myproject.twitter.dto.request.TweetRequestDto;
import com.myproject.twitter.dto.response.CommentResponseDto;
import com.myproject.twitter.dto.response.LikeResponseDto;
import com.myproject.twitter.dto.response.RetweetResponseDto;
import com.myproject.twitter.dto.response.TweetResponseDto;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> findAll();
    TweetResponseDto create(TweetRequestDto tweetRequestDto);

    TweetResponseDto findById(Long id);
    TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto);
    TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto);
    void deleteById(Long id);

    List<TweetResponseDto> findByUserId();

    List<LikeResponseDto> getLikes(Long id);
    List<CommentResponseDto> getComments(Long id);
    List<RetweetResponseDto> getRetweets(Long id);

    TweetResponseDto assignLike(Long tweetId, Long likeId);
    TweetResponseDto assignComment(Long tweetId, Long commentId);
    TweetResponseDto assignRetweet(Long tweetId, Long retweetId);

    void removeLike(Long tweetId, Long likeId);
    void removeComment(Long tweetId, Long commentId);
    void removeRetweet(Long tweetId, Long retweetId);

    List<TweetResponseDto> searchTweetByContext(String text);


}
