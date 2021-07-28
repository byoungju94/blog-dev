package com.byoungju94.blog.content.repository;

import com.byoungju94.blog.content.Content;

import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, Long>, ContentReadRepository {

}
