package com.byoungju94.blog.comment.repository;

import java.util.List;

import com.byoungju94.blog.comment.Comment;

public interface CommentReadRepository {

    List<Comment> findByPostIdWithPaging(String postId, int startPage, int perPageSize);
    
}
