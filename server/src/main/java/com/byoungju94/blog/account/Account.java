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
@Table("tbl_content")
public class Account implements Persistable<Long> {

    @Id
    private Long id;

    private String uuid;
    private String username;
    private String password;
    private Instant createdAt;
    private AccountState state;

    @Transient
    private boolean isNew = true;
    
    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Account(String uuid, String username, String password, Instant createdAt) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    @PersistenceConstructor
    public Account(Long id, String uuid, String password, Instant createdAt) {
        this.id = id;
        this.uuid = uuid;
        this.password = password;
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
