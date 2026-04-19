package com.myproject.twitter.repository;

import com.myproject.twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :content, '%'))
    """)
    List<Comment> searchByContent(@Param("content") String content);
}
