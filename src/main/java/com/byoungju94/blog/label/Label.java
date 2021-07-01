package com.byoungju94.blog.label;

import java.util.UUID;

import com.byoungju94.blog.post.Post;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Label implements Persistable<UUID> {
    
    @Id
    private UUID id;

    private String name;
    private String color;
    private AggregateReference<Post, UUID> postId;

    @Transient
    @Builder.Default
    private Boolean isNew = true;

    @PersistenceConstructor
    public Label(UUID id, String name, String color, AggregateReference<Post, UUID> postId) {
        this.name = name;
        this.color = color;
        this.postId = postId;
        this.isNew = false;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
    
    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Component
    public static class LabelAfterSaveEventListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Label) {
                ((Label) event.getEntity()).isNew = false;
            }
        }
    }
}
