package com.byoungju94.blog.post.repository;

import com.byoungju94.blog.post.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PostReadRepository {

}
