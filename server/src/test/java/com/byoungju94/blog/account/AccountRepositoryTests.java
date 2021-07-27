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

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AccountRepositoryTests {

    @Autowired
    AccountRepository sut;

    @BeforeAll
    void initialize() {

    }

    @AfterAll
    void cleanUp() {
        sut.deleteAll();
    }

    @Test
    void create() {
        // given
        var account = this.accounts.get(0);

        // when
        var actual = this.sut.save(account);

        // then
        assertEquals(account, actual);
    }

    @Test
    void delete() {
        // given
        var account = this.accounts.get(0);
        account.delete();

        // when
        this.sut.save(account);
        var actual = this.sut.findByIdLatestEvent(account.getId());

        // then
        assertEquals(AccountState.DELETED.toString(), actual.status());
    }

    @Test
    void findAllStateActive() {
        // when
        var accounts = this.sut.findAllStateActive();

        // then
        assertEquals(0, accounts.size());
    }

    private final List<Account> accounts = List.of(
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
}
