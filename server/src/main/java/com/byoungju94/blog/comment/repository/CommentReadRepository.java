package com.byoungju94.blog.comment.repository;

import java.util.List;

import com.byoungju94.blog.comment.dto.CommentDTO;

public interface CommentReadRepository {

    List<CommentDTO> findByPostIdWithPaging(String postId, int startPage, int perPageSize);
}
