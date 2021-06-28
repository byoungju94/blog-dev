package com.byoungju94.blog.label;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Label implements Persistable<UUID> {
    
    @Id
    private UUID id;

    private String name;
    private String color;

    @JsonIgnore
    private Boolean isInsert;

    @Override
    public UUID getId() {
        return this.id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return this.isInsert;
    }

    public static class LabelAfterSaveEventListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Label) {
                ((Label) event.getEntity()).isInsert = false;
            }
        }
    }
}
