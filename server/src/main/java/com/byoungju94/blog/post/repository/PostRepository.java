package com.byoungju94.blog.post.repository;

import com.byoungju94.blog.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>, PostReadRepository {

    @Query("SELECT p.* FROM post p WHERE p.categoryId = :categoryId ORDER BY p.id DESC")
    Page<Post> findByCategoryIdWithPaging(@Param("categoryId") String categoryId, Pageable pageable);

    @Query("SELECT p.* FROM post p WHERE p.categoryId = :categoryId AND p.title LIKE '%:title%' AND p.id > 0")
    Page<Post> findByCategoryIdAndTitleWithPaging(@Param("categoryId") String categoryId, @Param("title") String title, Pageable pageable);
}
