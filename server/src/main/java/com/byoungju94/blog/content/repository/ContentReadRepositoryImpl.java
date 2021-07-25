package com.byoungju94.blog.content.repository;

import com.byoungju94.blog.content.Content;
import com.byoungju94.blog.content.ContentState;
import com.byoungju94.blog.content.dto.ContentDTO;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.time.Instant;
import java.util.List;

public class ContentReadRepositoryImpl implements ContentReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public ContentReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<ContentDTO> rowMapperDTO = ((rs, rowNum) -> new ContentDTO(
            rs.getString("file_path"),
            rs.getLong("order_num")
    ));

    RowMapper<Content> rowMapper = ((rs, rowNum) -> Content.builder()
            .uuid(rs.getString("uuid"))
            .filePath(rs.getString("filePath"))
            .state(ContentState.valueOf(rs.getString("state")))
            .orderNum(rs.getLong("order_num"))
            .postId(AggregateReference.to(rs.getLong("post_id")))
            .createdAt(Instant.parse(rs.getString("created_at")))
            .build()
    );

    @Override
    public List<ContentDTO> findByPostId(String postId) {
        var param = new MapSqlParameterSource()
                .addValue("post_id", postId)
                .addValue("state", ContentState.OPENED);
        var query = ContentNativeQuerySQL.findByPostId;

        return this.jdbcOperations.query(query, param, rowMapperDTO);
    }

    @Override
    public Content findByLatestOfUuid(String uuid) {
        var param = new MapSqlParameterSource()
                .addValue("uuid", uuid);
        var query = ContentNativeQuerySQL.findByLatestOfUuid;

        return this.jdbcOperations.queryForObject(query, param, rowMapper);
    }
}
