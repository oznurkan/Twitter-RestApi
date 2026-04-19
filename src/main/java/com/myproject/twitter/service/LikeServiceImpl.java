package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.LikeRequestDto;
import com.myproject.twitter.dto.response.LikeResponseDto;
import com.myproject.twitter.entity.Like;
import com.myproject.twitter.entity.Tweet;
import com.myproject.twitter.entity.User;
import com.myproject.twitter.exception.TweetNotFoundException;
import com.myproject.twitter.exception.TwitterConflictException;
import com.myproject.twitter.exception.TwitterNotFoundException;
import com.myproject.twitter.repository.LikeRepository;
import com.myproject.twitter.repository.TweetRepository;
import com.myproject.twitter.util.mapper.LikeMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl extends BaseService implements LikeService{

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private LikeMapper likeMapper;

    @Override
    public List<LikeResponseDto> findAll() {
        return likeRepository
                .findAll()
                .stream()
                .map(likeMapper::toResponseDto)
                .toList();
    }

    @Override
    public LikeResponseDto findById(Long id) {

        Optional<Like> optionalLike = likeRepository.findById(id);

        if( optionalLike.isPresent() ){

            Like like = optionalLike.get();

            return likeMapper.toResponseDto(like);
        }

        throw new TwitterNotFoundException("like bulunamadı, id: " +id);
    }

    @Override
    @Transactional
    public LikeResponseDto create(LikeRequestDto likeRequestDto) {

        User currentUser = getCurrentUser();

        Tweet tweet = tweetRepository.findById(likeRequestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı"));


        if (likeRepository.existsByUserIdAndTweetId(currentUser.getId(), tweet.getId())) {
            throw new TwitterConflictException("Bu tweeti zaten beğendiniz!");
        }

        Like like = likeMapper.toEntity(likeRequestDto);
        like.setUser(currentUser);
        like.setTweet(tweet);

        tweet.addLike(like);


        likeRepository.save(like);
        return likeMapper.toResponseDto(like);

    }

    @Override
    @Transactional
    public void deleteLike(LikeRequestDto likeRequestDto) {

        User currentUser = getCurrentUser();

        Like like = likeRepository.findByUserIdAndTweetId(currentUser.getId(), likeRequestDto.tweetId())
                .orElseThrow(() -> new TwitterNotFoundException("Bu tweet için beğeni bulunamadı."));

        if (like.getTweet() != null) {
            like.getTweet().getLikes().remove(like);
        }

        if (like.getUser() != null) {
            like.getUser().getLikes().remove(like);
        }

        likeRepository.delete(like);

    }

}
