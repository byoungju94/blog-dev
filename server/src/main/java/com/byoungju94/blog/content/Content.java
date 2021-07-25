package com.byoungju94.blog.content;

import com.byoungju94.blog.post.Post;
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
public class Content implements Persistable<Long> {

    @Id
    private Long id;

    private String uuid;
    private String filePath;
    private ContentState state;
    private Long orderNum;
    private Instant createdAt;

    private AggregateReference<Post, Long> postId;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void delete() {
        this.state = ContentState.DELETED;
    }

    @Builder
    public Content(String filePath, String uuid, ContentState state, Long orderNum, AggregateReference<Post, Long> postId, Instant createdAt) {
        this.filePath = filePath;
        this.uuid = uuid;
        this.state = state;
        this.orderNum = orderNum;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    @PersistenceConstructor
    public Content(Long id, String uuid, String filePath, ContentState state, Long orderNum, AggregateReference<Post, Long> postId, Instant createdAt) {
        this.id = id;
        this.uuid = uuid;
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
