package com.byoungju94.blog.comment;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
    
}
