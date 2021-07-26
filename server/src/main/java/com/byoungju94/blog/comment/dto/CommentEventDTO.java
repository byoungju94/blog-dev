package com.byoungju94.blog.comment.dto;

import java.util.UUID;

public record CommentEventDTO(UUID id, String content, String postId, String accountId) {   
}
