package com.byoungju94.blog.domain.post;

import java.time.Instant;

import com.byoungju94.blog.domain.account.Account;
import com.byoungju94.blog.domain.category.Category;

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
public class Post implements Persistable<String> {

    @Id
    private String id;

    private String title;
    private PostState state;
    private Instant createdAt;

    @Column("category_id")
    private AggregateReference<Category, Long> categoryId;

    @Column("account_id")
    private AggregateReference<Account, String> accountId;

    @Transient
    private Boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Post(String id, 
                String title, 
                PostState state, 
                Instant createdAt, 
                AggregateReference<Category, Long> categoryId, 
                AggregateReference<Account, String> accountId,
                boolean isNew) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Post(String id, 
                String title, 
                PostState state, 
                Instant createdAt, 
                AggregateReference<Category, Long> categoryId,
                AggregateReference<Account, String> accountId) {
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
