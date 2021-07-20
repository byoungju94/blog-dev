package com.byoungju94.blog.post;

import java.util.UUID;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JdbcAggregateOperations jdbcAggregateOperations;

    public PostRepositoryImpl(JdbcAggregateOperations jdbcAggregateOperations) {
        this.jdbcAggregateOperations = jdbcAggregateOperations;
    }

    @Transactional
    public void deleteById(UUID id) {
        Post post = this.jdbcAggregateOperations.findById(id, Post.class);
        if (post == null) {
            throw new TransientDataAccessResourceException("invalid post id: " + id);
        }

        this.delete(post);
    }

    @Transactional
    public void delete(Post entity) {
        entity.delete();

        this.jdbcAggregateOperations.update(entity);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Post> entites) {
        entites.forEach(this::delete);
    }

    @Transactional
    public void deleteAll() {
        Iterable<Post> posts = this.jdbcAggregateOperations.findAll(Post.class);
        this.deleteAll(posts);
    }
}
