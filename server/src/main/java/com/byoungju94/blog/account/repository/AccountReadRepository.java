package com.byoungju94.blog.account.repository;

import java.util.List;

import com.byoungju94.blog.account.dto.AccountReadDTO;

public interface AccountReadRepository {
    
    List<AccountReadDTO> findAllStateActive();
}
