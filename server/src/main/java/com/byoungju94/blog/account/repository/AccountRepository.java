package com.byoungju94.blog.account.repository;

import java.util.UUID;

import com.byoungju94.blog.account.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String>, AccountReadRepository {
    
}
