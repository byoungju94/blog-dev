package com.byoungju94.blog.domain.content.dto;

public record ContentEventDTO(
        String id, 
        String filePath, 
        String orderNum, 
        String postId
) {
}
