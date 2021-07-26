package com.byoungju94.blog.comment.repository;

import java.util.List;

import com.byoungju94.blog.comment.dto.CommentDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class CommentReadRepositoryImpl implements CommentReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public CommentReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<CommentDTO> rowMapperDTO = ((rs, rowNum) -> new CommentDTO(
            rs.getString("uuid"), 
            rs.getString("content"), 
            rs.getString("name")
    ));

    @Override
    public List<CommentDTO> findByPostIdWithPaging(String postId, int startPage, int amount) {
        var param = new MapSqlParameterSource()
            .addValue("post_id", postId)
            .addValue("start_page", startPage)
            .addValue("amount", amount);
        var query = CommentNativeQuerySQL.FIND_BY_POST_ID_WITH_PAGING;

        return this.jdbcOperations.query(query, param, rowMapperDTO);
    }    
}
