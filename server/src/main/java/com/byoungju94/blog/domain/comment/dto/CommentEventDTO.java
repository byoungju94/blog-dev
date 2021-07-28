package com.byoungju94.blog.domain.comment.dto;

public record CommentEventDTO(
        String id,
        String content,
        String postId,
        String accountId
) {
}
