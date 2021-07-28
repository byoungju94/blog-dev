package com.byoungju94.blog.domain.content.repository;

import java.util.List;

import com.byoungju94.blog.domain.content.ContentState;
import com.byoungju94.blog.domain.content.dto.ContentEventDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class ContentReadRepositoryImpl implements ContentReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public ContentReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<ContentEventDTO> rowMapperDTO = ((rs, rowNum) -> new ContentEventDTO(
            rs.getString("id"),
            rs.getString("file_path"),
            rs.getString("order_num"),
            rs.getString("post_id")
    ));

    @Override
    public List<ContentEventDTO> findByPostId(String postId) {
        var param = new MapSqlParameterSource()
                .addValue("postId", postId)
                .addValue("state", ContentState.OPENED);
        var query = ContentNativeQuerySQL.findByPostId;

        return this.jdbcOperations.query(query, param, rowMapperDTO);
    }

    @Override
    public ContentEventDTO findByIdLatestEvent(String id) {
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        var query = ContentNativeQuerySQL.findByIdLatestEvent;

        return this.jdbcOperations.queryForObject(query, param, rowMapperDTO);
    }
}
