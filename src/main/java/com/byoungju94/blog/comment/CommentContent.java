package com.byoungju94.blog.comment;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentContent {

    private String body;
    private String mimeType;
}
