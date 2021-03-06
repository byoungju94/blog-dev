package com.byoungju94.blog.domain.account.repository;

import java.util.List;

import com.byoungju94.blog.domain.account.dto.AccountReadDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class AccountReadRepositoryImpl implements AccountReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AccountReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<AccountReadDTO> rowMapperReadDTO = ((rs, rowNum) -> new AccountReadDTO(
            rs.getString("id"),
            rs.getString("username"),
            rs.getString("name"),
            rs.getString("state")
    ));

    @Override
    public List<AccountReadDTO> findAllStateActive() {
        var query = AccountNativeQuerySQL.findAllStateActive;
        return this.jdbcOperations.query(query, rowMapperReadDTO);
    }

    @Override
    public AccountReadDTO findByIdLatestEvent(String id) {
        var param = new MapSqlParameterSource()
            .addValue("id", id);

        var query = AccountNativeQuerySQL.findById;
        return this.jdbcOperations.queryForObject(query, param, rowMapperReadDTO);
    }
    
}
