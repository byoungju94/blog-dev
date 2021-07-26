package com.byoungju94.blog.post.dto;

import java.time.Instant;
import java.util.UUID;

public record PostDTO(UUID uuid, String title, Instant createdAt) {
}
