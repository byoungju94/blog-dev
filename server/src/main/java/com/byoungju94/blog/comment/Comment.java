package com.byoungju94.blog.comment;

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

@Getter
@Table("tbl_comment")
public class Comment implements Persistable<Long> {

    private Long id;

    @Id
    private String uuid;
    private String content;
    private CommentState state;

    private AggregateReference<Post, Long> postId;
    private AggregateReference<Account, Long> accountId;

    @Transient
    private boolean isNew = true;
    
    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Comment(String uuid, 
                   String content,
                   CommentState state,
                   AggregateReference<Post, Long> postId,
                   AggregateReference<Account, Long> accountId) {
        this.uuid = uuid;
        this.content = content;
        this.state = state;
        this.postId = postId;
        this.accountId = accountId;
    }

    @PersistenceConstructor
    public Comment(Long id, 
                   String uuid, 
                   String content, 
                   CommentState state,
                   AggregateReference<Post, Long> postId,
                   AggregateReference<Account, Long> accountId) {
        this.id = id;
        this.uuid = uuid;
        this.content = content;
        this.state = state;
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
