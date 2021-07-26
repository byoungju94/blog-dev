package com.byoungju94.blog.account.repository;

import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.account.dto.AccountReadDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class AccountReadRepositoryImpl implements AccountReadRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AccountReadRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    RowMapper<AccountReadDTO> rowMapperReadDTO = ((rs, rowNum) -> new AccountReadDTO(
            rs.getString("id"),
            rs.getString("username"),
            rs.getString("name")
    ));

    @Override
    public List<AccountReadDTO> findAllStateActive() {
        var query = AccountNativeQuerySQL.findAllStateActive;
        return this.jdbcOperations.query(query, rowMapperReadDTO);
    }
    
}
