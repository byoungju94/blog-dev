package com.byoungju94.blog.domain.post.repository;

import com.byoungju94.blog.domain.post.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PostReadRepository {

}
