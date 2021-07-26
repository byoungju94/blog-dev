package com.byoungju94.blog.comment;

import java.time.Instant;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.post.Post;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table("tbl_comment")
public class Comment implements Persistable<UUID> {

    @Id
    private UUID id;
    private String content;
    private CommentState state;
    private Instant createdAt;

    private AggregateReference<Post, UUID> postId;
    private AggregateReference<Account, UUID> accountId;

    @Transient
    private boolean isNew = true;
    
    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Comment(UUID id, 
                   String content,
                   CommentState state,
                   Instant createdAt,
                   AggregateReference<Post, UUID> postId,
                   AggregateReference<Account, UUID> accountId,
                   boolean isNew) {
        this.id = id;
        this.content = content;
        this.state = state;
        this.createdAt = createdAt;
        this.postId = postId;
        this.accountId = accountId;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Comment(UUID id, 
                   String content, 
                   CommentState state,
                   Instant createdAt,
                   AggregateReference<Post, UUID> postId,
                   AggregateReference<Account, UUID> accountId) {
        this.id = id;
        this.content = content;
        this.state = state;
        this.createdAt = createdAt;
        this.postId = postId;
        this.accountId = accountId;
        this.isNew = false;
    }
    
    @Component
    public static class CommentAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Comment) {
                ((Comment) event.getEntity()).isNew = false;
            }
        }
    }
}
