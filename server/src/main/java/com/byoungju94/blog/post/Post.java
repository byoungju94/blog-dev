package com.byoungju94.blog.post;

import java.time.Instant;
import java.util.UUID;

import com.byoungju94.blog.category.Category;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Table("tbl_post")
public class Post implements Persistable<UUID> {

    @Id
    private UUID id;

    private String title;
    private PostState state;
    private Instant createdAt;

    @Column("CATEGORY_ID")
    private AggregateReference<Category, UUID> categoryId;

    @Transient
    private Boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Post(String title, PostState state, Instant createdAt, AggregateReference<Category, UUID> categoryId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
    }

    @PersistenceConstructor
    public Post(UUID id, String title, PostState state, Instant createdAt, AggregateReference<Category, UUID> categoryId) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
        this.isNew = false;
    }

    @Component
    public static class PostAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Post) {
                ((Post) event.getEntity()).isNew = false;
            }
        }
    }
}
