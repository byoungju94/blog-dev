package com.byoungju94.blog.comment.repository;

import com.byoungju94.blog.comment.Comment;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>, CommentReadRepository {
    
}
