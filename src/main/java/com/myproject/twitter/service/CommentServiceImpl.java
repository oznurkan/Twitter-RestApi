package com.myproject.twitter.service;

import com.myproject.twitter.dto.request.CommentPatchRequestDto;
import com.myproject.twitter.dto.request.CommentRequestDto;
import com.myproject.twitter.dto.response.CommentResponseDto;
import com.myproject.twitter.entity.Comment;
import com.myproject.twitter.entity.Tweet;
import com.myproject.twitter.exception.TwitterNotFoundException;
import com.myproject.twitter.repository.CommentRepository;
import com.myproject.twitter.repository.TweetRepository;
import com.myproject.twitter.util.mapper.CommentMapper;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl extends BaseService implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<CommentResponseDto> getAll() {

        return commentRepository
            .findAll()
            .stream()
            .map(commentMapper::toResponseDto)
            .toList();
    }

    @Override
    public CommentResponseDto findById(Long id) {

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if( optionalComment.isPresent() ){

            Comment comment = optionalComment.get();

            return commentMapper.toResponseDto(comment);
        }

        throw new TwitterNotFoundException("comment bulunamadı , id: " +id);
    }



    @Override
    @Transactional
    public CommentResponseDto replaceOrCreate(Long id, CommentRequestDto commentRequestDto) {

        Comment comment = commentMapper.toEntity(commentRequestDto);

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if( optionalComment.isPresent() ){

            comment.setId(id);

            verifyOwnership(comment.getUser().getEmail());

            commentRepository.save(comment);

            return commentMapper.toResponseDto(comment);
        }

        comment.setUser(getCurrentUser());

        commentRepository.save(comment);

        return commentMapper.toResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto update(Long id, CommentPatchRequestDto commentPatchRequestDto){

        Comment commentToUpdate = commentRepository
                .findById(id)
                .orElseThrow(() -> new TwitterNotFoundException("comment bulunumadı, id: " +id));

        verifyOwnership(commentToUpdate.getUser().getEmail());

        if (commentPatchRequestDto.tweetId() != null && !commentToUpdate.getTweet().getId().equals(commentPatchRequestDto.tweetId())) {
            throw new TwitterNotFoundException("Bu yorum bu tweet'e ait değil!");
        }


        commentMapper.updateEntity(commentToUpdate, commentPatchRequestDto);

        commentRepository.save(commentToUpdate);

        return commentMapper.toResponseDto(commentToUpdate);


    }

    @Override
    @Transactional
    public CommentResponseDto create(CommentRequestDto commentRequestDto) {

        Tweet tweet = tweetRepository.findById(commentRequestDto.tweetId())
                .orElseThrow(() -> new TwitterNotFoundException("Tweet bulunamadı"));


        Comment comment = commentMapper.toEntity(commentRequestDto);
        comment.setTweet(tweet);
        comment.setUser(getCurrentUser());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponseDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterNotFoundException("Yorum bulunamadı, id: " + id));

        verifyCommentDeletionAuth(
                comment.getUser().getId(),
                comment.getTweet().getUser().getId()
        );

        commentRepository.deleteById(id);
    }


    @Override
    public List<CommentResponseDto> searchByContent(String content) {
        return commentRepository
                .searchByContent(content)
                .stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }


}
