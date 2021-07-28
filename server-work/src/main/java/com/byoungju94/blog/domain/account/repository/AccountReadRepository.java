package com.byoungju94.blog.domain.account.repository;

import java.util.List;

import com.byoungju94.blog.domain.account.dto.AccountReadDTO;

public interface AccountReadRepository {
    
    List<AccountReadDTO> findAllStateActive();

    AccountReadDTO findByIdLatestEvent(String id);
}
