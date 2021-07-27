package com.byoungju94.blog.post.dto;

import java.time.Instant;

public record PostDTO(String id, String title, String state, String createdAt) {
}
