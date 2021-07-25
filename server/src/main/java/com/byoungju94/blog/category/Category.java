package com.byoungju94.blog.category;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Category implements Persistable<UUID> {
    
    @Id
    private UUID id;

    private String name;

    @Builder.Default
    @Transient
    private Boolean isNew = true;

    @PersistenceConstructor
    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
