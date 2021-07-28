package com.byoungju94.blog.controller.exception;

import java.time.Instant;

public record ExceptionResponse(
        Instant timestamp,
        String message,
        String details
) {

}
