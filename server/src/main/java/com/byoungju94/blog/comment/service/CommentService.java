package com.byoungju94.blog.comment.service;

import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.comment.Comment;
import com.byoungju94.blog.comment.CommentState;
import com.byoungju94.blog.comment.dto.CommentEventDTO;
import com.byoungju94.blog.comment.dto.CommentDTO;
import com.byoungju94.blog.comment.repository.CommentRepository;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Comment create(CommentEventDTO dto) {
        var comment = Comment.builder()
                .id(UUID.randomUUID())
                .content(dto.content())
                .state(CommentState.CREATED)
                .postId(AggregateReference.to(UUID.fromString(dto.postId())))
                .accountId(AggregateReference.to(UUID.fromString(dto.accountId())))
                .isNew(true)
                .build();

        return this.repository.save(comment);
    }

    @Transactional
    public List<CommentDTO> getCommentsWithPaging(String postId, int startPage, int amount) {
        return this.repository.findByPostIdWithPaging(postId, startPage, amount);
    }    
}
