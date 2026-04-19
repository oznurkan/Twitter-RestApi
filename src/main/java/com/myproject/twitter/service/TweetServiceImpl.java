package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.TweetPatchRequestDto;
import com.myproject.twitter.dto.request.TweetRequestDto;
import com.myproject.twitter.dto.response.CommentResponseDto;
import com.myproject.twitter.dto.response.LikeResponseDto;
import com.myproject.twitter.dto.response.RetweetResponseDto;
import com.myproject.twitter.dto.response.TweetResponseDto;
import com.myproject.twitter.entity.*;
import com.myproject.twitter.exception.TweetNotFoundException;
import com.myproject.twitter.exception.TwitterNotFoundException;
import com.myproject.twitter.repository.*;
import com.myproject.twitter.util.mapper.CommentMapper;
import com.myproject.twitter.util.mapper.LikeMapper;
import com.myproject.twitter.util.mapper.RetweetMapper;
import com.myproject.twitter.util.mapper.TweetMapper;
import com.myproject.twitter.util.security.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl extends BaseService implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RetweetRepository retweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RetweetMapper retweetMapper;

    @Override
    public List<TweetResponseDto> findAll() {

        String currentUserEmail = SecurityUtils.getCurrentUserEmail();


        return tweetRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(tweet -> tweetMapper.toResponseDto(tweet, currentUserEmail))
                .toList();
    }

    @Override
    public TweetResponseDto findById(Long id) {

        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        String currentUserEmail = SecurityUtils.getCurrentUserEmail();

        if( optionalTweet.isPresent()){

            Tweet tweet = optionalTweet.get();

            return tweetMapper.toResponseDto(tweet, currentUserEmail);
        }


        throw new TweetNotFoundException("Tweet bulunamadi , id: " +id);
    }

    @Override
    @Transactional
    public TweetResponseDto create(TweetRequestDto tweetRequestDto) {

        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);

        User author = getCurrentUser();

        tweet.setUser(author);

        tweetRepository.save(tweet);

        return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail());
    }


    @Override
    @Transactional
    public TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto) {

        Tweet tweetToUpdate = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı"));

        verifyOwnership(tweetToUpdate.getUser().getEmail());

        tweetMapper.updateEntity(tweetToUpdate, tweetPatchRequestDto);

        tweetRepository.save(tweetToUpdate);

        return tweetMapper.toResponseDto(tweetToUpdate, getCurrentUser().getEmail());
    }

    @Override
    @Transactional
    public TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto) {


        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);

        Optional<Tweet> optionalTweet = tweetRepository.findById(id);


        if( optionalTweet.isPresent() ){

            tweet.setId(id);

            verifyOwnership(tweet.getUser().getEmail());

            tweetRepository.save(tweet);

            return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail());

        }

        tweet.setUser(getCurrentUser());

        tweetRepository.save(tweet);
        return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TwitterNotFoundException("Tweet bulunamadı , id:" +id));

        verifyOwnership(tweet.getUser().getEmail());

        tweetRepository.deleteById(id);

    }

    @Override
    public List<TweetResponseDto> findByUserId() {

        User author = getCurrentUser();

        return tweetRepository.findByUserId(author.getId())
                .stream()
                .map((tweet) -> tweetMapper.toResponseDto( tweet,getCurrentUser().getEmail()))
                .toList();
    }

    @Override
    public List<LikeResponseDto> getLikes(Long id) {

        Tweet tweet = tweetRepository
                .findById(id)
                .orElseThrow(()-> new TwitterNotFoundException("tweet bulunamadi, id : " + id));

        return tweet
                .getLikes()
                .stream()
                .map(likeMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> getComments(Long id) {

        Tweet tweet = tweetRepository
                .findById(id)
                .orElseThrow(()-> new TwitterNotFoundException("tweet bulunamadi, id : " + id));


        return tweet
                .getComments()
                .stream()
                .map(commentMapper::toResponseDto)
                .toList();


    }

    @Override
    public List<RetweetResponseDto> getRetweets(Long id) {

        Tweet tweet = tweetRepository
                .findById(id)
                .orElseThrow(()-> new TwitterNotFoundException("tweet bulunamadi, id : " + id));

        return tweet
                .getRetweets()
                .stream()
                .map(retweetMapper::toResponseDto)
                .toList();


    }

    @Override
    @Transactional
    public TweetResponseDto assignLike(Long tweetId, Long likeId) {


        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Like like = likeRepository
                .findById(likeId)
                .orElseThrow(()-> new TwitterNotFoundException("like bulunamadi, id : " + likeId));

        tweet.addLike(like);
        like.setTweet(tweet);

        tweetRepository.save(tweet);

        return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail() );
    }

    @Override
    @Transactional
    public TweetResponseDto assignComment(Long tweetId, Long commentId) {

        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(()-> new TwitterNotFoundException("Comment bulunamadi, id : " + commentId));

        tweet.addComment(comment);
        comment.setTweet(tweet);

        tweetRepository.save(tweet);

        return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail());
    }

    @Override
    @Transactional
    public TweetResponseDto assignRetweet(Long tweetId, Long retweetId) {
        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Retweet retweet = retweetRepository
                .findById(retweetId)
                .orElseThrow(()-> new TwitterNotFoundException("Retweet bulunamadi, id : " + retweetId));

        tweet.addRetweet(retweet);
        retweet.setTweet(tweet);

        tweetRepository.save(tweet);

        return tweetMapper.toResponseDto(tweet, getCurrentUser().getEmail());
    }

    @Override
    @Transactional
    public void removeLike(Long tweetId, Long likeId) {

        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Like like = likeRepository
                .findById(likeId)
                .orElseThrow(()-> new TwitterNotFoundException("like bulunamadi, id : " + likeId));

        tweet.deleteLike(like);
        like.setTweet(null);

        tweetRepository.save(tweet);

    }

    @Override
    @Transactional
    public void removeComment(Long tweetId, Long commentId) {

        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(()-> new TwitterNotFoundException("Comment bulunamadi, id : " + commentId));

        tweet.deleteComment(comment);
        comment.setTweet(null);

        tweetRepository.save(tweet);


    }

    @Override
    @Transactional
    public void removeRetweet(Long tweetId, Long retweetId) {

        Tweet tweet = tweetRepository
                .findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException("tweet bulunamadi, id : " + tweetId));

        Retweet retweet = retweetRepository
                .findById(retweetId)
                .orElseThrow(()-> new TwitterNotFoundException("Retweet bulunamadi, id : " + retweetId));

        tweet.deleteRetweet(retweet);
        retweet.setTweet(null);

        tweetRepository.save(tweet);

    }

    @Override
    public List<TweetResponseDto> searchTweetByContext(String text){

        return tweetRepository
                .searchTweetByContext(text)
                .stream()
                .map((tweet) -> tweetMapper.toResponseDto( tweet,getCurrentUser().getEmail()))
                .toList();
    }



}
