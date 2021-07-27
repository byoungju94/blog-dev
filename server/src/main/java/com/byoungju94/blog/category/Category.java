package com.byoungju94.blog.category;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Table("tbl_category")
public class Category implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Builder
    public Category(String name, boolean isNew) {
        this.name = name;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Category(Long id, String uuid, String name) {
        this.id = id;
        this.name = name;
        this.isNew = false;
    }

    @Component
    public static class CategoryAfterSaveListener implements ApplicationListener<AfterSaveEvent<?>>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public void onApplicationEvent(AfterSaveEvent<?> event) {
            if (event.getEntity() instanceof Category) {
                ((Category) event.getEntity()).isNew = false;
            }
        }

    }

}
