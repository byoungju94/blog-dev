package com.byoungju94.blog.domain.content.repository;

import com.byoungju94.blog.domain.content.Content;

import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, Long>, ContentReadRepository {

}
