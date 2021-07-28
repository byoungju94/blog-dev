package com.byoungju94.blog.domain.content;

import com.byoungju94.blog.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
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

import java.time.Instant;

@Getter
@Table("tbl_content")
public class Content implements Persistable<String> {

    @Id
    private String id;

    private String filePath;
    private ContentState state;
    private Long orderNum;
    private Instant createdAt;

    private AggregateReference<Post, String> postId;

    @Transient
    private boolean isNew = true;

    public void delete() {
        this.state = ContentState.DELETED;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Content(String id, String filePath, ContentState state, Long orderNum, AggregateReference<Post, String> postId, Instant createdAt, boolean isNew) {
        this.id = id;
        this.filePath = filePath;
        this.state = state;
        this.orderNum = orderNum;
        this.postId = postId;
        this.createdAt = createdAt;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Content(String id, String filePath, ContentState state, Long orderNum, AggregateReference<Post, String> postId, Instant createdAt) {
        this.id = id;
        this.filePath = filePath;
        this.state = state;
        this.orderNum = orderNum;
        this.postId = postId;
        this.createdAt = createdAt;
        this.isNew = false;
    }

    @Component
    public static class ContentAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Content) {
                ((Content) event.getEntity()).isNew = false;
            }
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }
    }
}
