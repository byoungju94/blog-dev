package com.byoungju94.blog.domain.comment.repository;

import java.util.List;

import com.byoungju94.blog.domain.comment.dto.CommentDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class CommentReadRepositoryImpl implements CommentReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public CommentReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<CommentDTO> rowMapperDTO = ((rs, rowNum) -> new CommentDTO(
            rs.getString("id"), 
            rs.getString("content"), 
            rs.getString("state"),
            rs.getString("created_at")
    ));

    @Override
    public List<CommentDTO> findByPostIdWithPaging(String postId, Pageable pageable) {
        var param = new MapSqlParameterSource()
            .addValue("postId", postId)
            .addValue("start", pageable.getPageNumber())
            .addValue("size", pageable.getPageSize());
        var query = CommentNativeQuerySQL.findByPostIdWithPaging;

        return this.jdbcOperations.query(query, param, rowMapperDTO);
    }

    @Override
    public CommentDTO findByIdLatestEvent(String id) {
        var param = new MapSqlParameterSource()
            .addValue("id", id);
        var query = CommentNativeQuerySQL.findByIdLatestEvent;
        
        return this.jdbcOperations.queryForObject(query, param, rowMapperDTO);
    }    
}
