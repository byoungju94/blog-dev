package com.byoungju94.blog.comment.repository;

import java.util.List;

import com.byoungju94.blog.comment.Comment;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class CommentReadRepositoryImpl implements CommentReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public CommentReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    

    @Override
    public List<Comment> findByPostIdWithPaging(String postId, int startPage, int perPageSize) {
        
        return null;
    }    
}
