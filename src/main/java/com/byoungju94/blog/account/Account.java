package com.byoungju94.blog.account;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Account implements Persistable<UUID> {
    
    @Id
    private UUID id;

    private String username;
    private String password;
    private String name;

    private AccountState state;

    @JsonIgnore
    private Boolean isInsert;

    @Builder.Default
    private Instant createdAt = Instant.now();

    public void lock() {
        this.state = AccountState.LOCKED;
    }

    public void delete() {
        this.state = AccountState.DELETED;
    }

    @Override
    public boolean isNew() {
        return isInsert;
    }

}
