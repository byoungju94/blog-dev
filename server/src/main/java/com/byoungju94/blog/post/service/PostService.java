package com.byoungju94.blog.post.service;

import java.util.List;

import com.byoungju94.blog.post.dto.PostDTO;
import com.byoungju94.blog.post.repository.PostRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public List<PostDTO> getAll(String categoryId, Pageable pageable) {
        return this.postRepository.findByCategoryIdWithPaging(Long.valueOf(categoryId), pageable);
    }
}
