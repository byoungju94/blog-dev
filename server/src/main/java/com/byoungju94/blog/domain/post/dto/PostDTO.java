package com.byoungju94.blog.domain.post.dto;

public record PostDTO(
        String id,
        String title,
        String state,
        String categoryId,
        String createdAt
) {
}
