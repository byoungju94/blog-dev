package com.byoungju94.blog.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository sut;

    @Test
    void insert() {
        // given
        Account account = accounts.stream().findFirst().get();

        // when
        Account actual = this.sut.save(account);

        // then
        assertEquals(account, actual);
    }

    @Test
    void softDelete() {
        // given
        Account account = accounts.stream().findFirst().get();
        account = this.sut.save(account);

        // when
        this.sut.delete(account);

        // then
        assertEquals(AccountState.DELETED, account.getState());

        Optional<Account> load = this.sut.findById(account.getId());
        assertTrue(load.isPresent());
        assertEquals(AccountState.DELETED, load.get().getState());

        Optional<Account> exclude = this.sut.findByIdExcludeDeleted(account.getId());
        assertEquals(Optional.empty(), exclude);
    }
    
    private final List<Account> accounts = List.of(
        Account.builder()
            .id(UUID.randomUUID())
            .username("byoungju94")
            .password("superPassword123")
            .name("Park Byoungju")
            .phone("01033425523")
            .isInsert(true)
            .build(),

        Account.builder()
            .id(UUID.randomUUID())
            .username("seoungsoo58")
            .password("superPassword123")
            .name("Park Seoungsoo")
            .phone("01055425542")
            .isInsert(true)
            .build()
    );
}
