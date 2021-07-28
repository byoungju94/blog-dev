package com.byoungju94.blog.domain.account.repository;

import com.byoungju94.blog.domain.account.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String>, AccountReadRepository {
    
}
