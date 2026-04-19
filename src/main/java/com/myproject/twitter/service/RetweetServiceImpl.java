package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.RetweetRequestDto;
import com.myproject.twitter.dto.response.RetweetResponseDto;
import com.myproject.twitter.entity.Retweet;
import com.myproject.twitter.entity.Tweet;
import com.myproject.twitter.entity.User;
import com.myproject.twitter.exception.TweetNotFoundException;
import com.myproject.twitter.exception.TwitterConflictException;
import com.myproject.twitter.exception.TwitterNotFoundException;
import com.myproject.twitter.repository.RetweetRepository;
import com.myproject.twitter.repository.TweetRepository;
import com.myproject.twitter.util.mapper.RetweetMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetweetServiceImpl extends BaseService implements RetweetService {

    @Autowired
    private RetweetRepository retweetRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private RetweetMapper retweetMapper;


    @Override
    public List<RetweetResponseDto> getAll() {
        return retweetRepository
                .findAll()
                .stream()
                .map(retweetMapper::toResponseDto)
                .toList();
    }

    @Override
    public RetweetResponseDto findById(Long id) {

        Optional<Retweet> optionalRetweet = retweetRepository.findById(id);

        if( optionalRetweet.isPresent()){

            Retweet retweet = optionalRetweet.get();

            return retweetMapper.toResponseDto(retweet);
        }

        throw new TwitterNotFoundException("retweet bulunamadı, id: " +id);
    }


    @Override
    @Transactional
    public RetweetResponseDto create(RetweetRequestDto retweetRequestDto) {

        User currentUser = getCurrentUser();

        Tweet tweet = tweetRepository.findById(retweetRequestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Retweet için tweet bulunamadı"));


        if (retweetRepository.existsByUserIdAndTweetId(currentUser.getId(), tweet.getId())) {
            throw new TwitterConflictException("Bu tweeti zaten retweet yaptınız!");
        }

        Retweet retweet = retweetMapper.toEntity(retweetRequestDto);
        retweet.setUser(currentUser);
        retweet.setTweet(tweet);

        tweet.addRetweet(retweet);


        retweetRepository.save(retweet);
        return retweetMapper.toResponseDto(retweet);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        User currentUser = getCurrentUser();

        Retweet retweet = retweetRepository.findByUserIdAndTweetId(currentUser.getId(), id)
                .orElseThrow(() -> new TwitterNotFoundException("Bu tweet için retweet bulunamadı"));


        if (retweet.getTweet() != null) {
            retweet.getTweet().getRetweets().remove(retweet);
        }

        if (retweet.getUser() != null) {
            retweet.getUser().getRetweets().remove(retweet);
        }

        retweetRepository.delete(retweet);
    }
}
