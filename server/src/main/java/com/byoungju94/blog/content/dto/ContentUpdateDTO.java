package com.byoungju94.blog.content.dto;

import com.byoungju94.blog.content.ContentState;

public record ContentUpdateDTO(
        String uuid,
        String filePath,
        Long orderNum,
        Long postId
) {
}
