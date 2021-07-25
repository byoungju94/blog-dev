package com.byoungju94.blog.post.dto;

import java.time.Instant;

public record PostDTO(String uuid, String title, Instant createdAt) {
}
