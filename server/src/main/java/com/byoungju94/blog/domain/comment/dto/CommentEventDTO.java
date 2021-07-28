package com.byoungju94.blog.domain.comment.dto;

import java.util.UUID;

public record CommentEventDTO(UUID id, String content, String postId, String accountId) {   
}
