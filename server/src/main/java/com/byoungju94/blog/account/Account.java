package com.byoungju94.blog.account;

import java.time.Instant;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Table("tbl_account")
public class Account implements Persistable<String> {

    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private Instant createdAt;
    private AccountState state;

    @Transient
    private boolean isNew = true;
    
    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void blocked() {
        this.state = AccountState.BLOCKED;
    }

    public void delete() {
        this.state = AccountState.DELETED;
    }

    @Builder
    public Account(String id, String username, String password, String name, AccountState state, Instant createdAt, boolean isNew) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.state = state;
        this.createdAt = createdAt;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Account(String id, String username, String password, String name, AccountState state, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.state = state;
        this.createdAt = createdAt;
        this.isNew = false;
    }

    @Component
    public static class AccountAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Account) {
                ((Account) event.getEntity()).isNew = false;
            }
        }
    }
}
