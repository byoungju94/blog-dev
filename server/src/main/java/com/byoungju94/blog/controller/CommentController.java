package com.byoungju94.blog.controller;

import com.byoungju94.blog.domain.comment.Comment;
import com.byoungju94.blog.domain.comment.dto.CommentDTO;
import com.byoungju94.blog.domain.comment.dto.CommentEventDTO;
import com.byoungju94.blog.domain.comment.repository.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentRepository repository;

    public CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all/{postId}")
    public List<CommentDTO> retrieveAll(@PathVariable String postId) {
        var pageable = PageRequest.of(0, 10);
        return this.repository.findByPostIdWithPaging(postId, pageable);
    }

    @GetMapping("/{postId}")
    public CommentDTO retrieve(@PathVariable String postId) {
        return this.repository.findByIdLatestEvent(postId);
    }

    @PatchMapping("/{commentId}")
    public Comment update(@RequestBody CommentEventDTO dto) {
        var comment = this.mappingEventToComment(dto);
        return this.repository.save(comment);
    }

    @DeleteMapping("/{commentId}")
    public Comment delete(@RequestBody CommentEventDTO dto) {
        var comment = this.mappingEventToComment(dto);
        return this.repository.save(comment);
    }

    private Comment mappingEventToComment(CommentEventDTO dto) {
        return Comment.builder()
                .id(dto.id())
                .content(dto.content())
                .createdAt(Instant.now())
                .postId(AggregateReference.to(dto.postId()))
                .accountId(AggregateReference.to(dto.accountId()))
                .isNew(true)
                .build();
    }
}
