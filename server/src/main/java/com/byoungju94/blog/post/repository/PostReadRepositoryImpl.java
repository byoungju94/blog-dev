package com.byoungju94.blog.post.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class PostReadRepositoryImpl implements PostReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }
}
