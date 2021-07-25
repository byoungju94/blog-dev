package com.byoungju94.blog.content.repository;

import com.byoungju94.blog.content.Content;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, Long>, ContentReadRepository {

}
