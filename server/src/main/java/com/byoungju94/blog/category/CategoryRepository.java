package com.byoungju94.blog.category;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
    
}
