package com.byoungju94.blog.domain.comment.repository;

import com.byoungju94.blog.domain.comment.Comment;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>, CommentReadRepository {
    
}
