package com.byoungju94.blog.content.dto;

public record ContentUpdateDTO(
        String uuid,
        String filePath,
        Long orderNum,
        Long postId
) {
}
