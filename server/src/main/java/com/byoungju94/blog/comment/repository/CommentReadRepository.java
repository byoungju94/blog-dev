package com.byoungju94.blog.comment.repository;

import java.util.List;

import com.byoungju94.blog.comment.dto.CommentDTO;

import org.springframework.data.domain.Pageable;

public interface CommentReadRepository {

    List<CommentDTO> findByPostIdWithPaging(String postId, Pageable pageable);

    CommentDTO findByIdLatestEvent(String commentId);
}
