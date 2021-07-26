package com.byoungju94.blog.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.account.repository.AccountRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AccountRepositoryTests {

    private final List<Account> account = List.of(
        Account.builder()
            .id(UUID.randomUUID().toString())
            .username("byoungju94")
            .password("superPassword123")
            .name("Byoungju Park")
            .createdAt(Instant.now())
            .state(AccountState.ACTIVE)
            .isNew(true)
            .build(),
        Account.builder()
            .id(UUID.randomUUID().toString())
            .username("byoungju94")
            .password("superPassword123")
            .name("Byoungju Park")
            .createdAt(Instant.now())
            .state(AccountState.ACTIVE)
            .isNew(true)
            .build()        
    );

    @Autowired
    AccountRepository sut;

    @BeforeAll
    public void initialize() {

    }

    @AfterAll
    public void clean() {
        sut.deleteAll();
    }

    @Test
    void create() {
        // given
        var account = this.account;

        // when
        var actual = this.sut.save(account);

        // then
        assertEquals(account, actual);
    }

    @Test
    void findAll() {
        // when
        var accounts = this.sut.findAllStateActive();
        assertEquals(1, accounts.size());
    }
    
}
