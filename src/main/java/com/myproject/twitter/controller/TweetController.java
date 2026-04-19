package com.myproject.twitter.controller;

import com.myproject.twitter.dto.request.TweetPatchRequestDto;
import com.myproject.twitter.dto.request.TweetRequestDto;
import com.myproject.twitter.dto.response.*;
import com.myproject.twitter.service.TweetService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAll(){

        return tweetService.findAll();
    }

    @GetMapping("/{id}")
    public TweetResponseDto findById(@Positive @PathVariable("id") Long id){

        return tweetService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto create(@Validated @RequestBody TweetRequestDto tweetRequestDto){

        return tweetService.create(tweetRequestDto);
    }

    @PutMapping("/{id}")
    public TweetResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id,@Validated @RequestBody TweetRequestDto tweetRequestDto){

        return tweetService.replaceOrCreate(id, tweetRequestDto);
    }

    @PatchMapping("/{id}")
    public TweetResponseDto update(@Positive @PathVariable("id") Long id,@Validated @RequestBody TweetPatchRequestDto tweetPatchRequestDto){

        return tweetService.update(id, tweetPatchRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id){

        tweetService.deleteById(id);
    }


    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getComments(@Positive @PathVariable("id") Long id){

        return tweetService.getComments(id);
    }

    @PatchMapping("/{tweetId}/comments/{commentId}")
    public TweetResponseDto assignComment(@PathVariable("tweetId") Long tweetId,
                                         @PathVariable("commentId") Long commentId){

        return tweetService.assignComment(tweetId, commentId);
    }

    @DeleteMapping("/{tweetId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void removeComment(@PathVariable("tweetId") Long tweetId,
                              @PathVariable("commentId") Long commentId){

        tweetService.removeComment(tweetId, commentId);
    }

    @GetMapping("/{id}/likes")
    public List<LikeResponseDto> getLikes(@Positive @PathVariable("id") Long id){

        return tweetService.getLikes(id);
    }

    @PatchMapping("/{tweetId}/likes/{likeId}")
    public TweetResponseDto assignLike(@PathVariable("tweetId") Long tweetId,
                                      @PathVariable("likeId") Long likeId){

        return tweetService.assignLike(tweetId, likeId);
    }

    @DeleteMapping("/{tweetId}/likes/{likeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void removeLike(@PathVariable("tweetId") Long tweetId,
                           @PathVariable("likeId") Long likeId){

        tweetService.removeLike(tweetId, likeId);
    }

    @GetMapping("/{id}/retweets")
    public List<RetweetResponseDto> getRetweets(@Positive @PathVariable("id") Long id){

        return tweetService.getRetweets(id);
    }

    @PatchMapping("/{tweetId}/retweets/{retweetId}")
    public TweetResponseDto assignRetweet(@PathVariable("tweetId") Long tweetId,
                                         @PathVariable("retweetId") Long retweetId){

        return tweetService.assignRetweet(tweetId, retweetId);
    }

    @DeleteMapping("/{tweetId}/retweets/{retweetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void removeRetweet(@PathVariable("tweetId") Long tweetId,
                              @PathVariable("retweetId") Long retweetId){

        tweetService.removeRetweet(tweetId, retweetId);
    }


    @GetMapping("/search")
    public List<TweetResponseDto> search(@Size(min = 3, max = 15) @RequestParam(name = "text") String text){

        return tweetService.searchTweetByContext(text);
    }

    @GetMapping("/findByUserId")
    public List<TweetResponseDto> findByUserId() {
        return tweetService.findByUserId();
    }


}
