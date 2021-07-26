package com.byoungju94.blog.post.service;

import com.byoungju94.blog.post.Post;
import com.byoungju94.blog.post.dto.PostDTO;
import com.byoungju94.blog.post.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public List<PostDTO> getAll(String categoryId, Pageable pageable) {
        return this.postRepository.findByCategoryIdWithPaging(categoryId, pageable)
                .stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDTO> searchByTitle(String categoryId, String title, Pageable pageable) {
        return this.postRepository.findByCategoryIdAndTitleWithPaging(categoryId, title)
                .stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    private PostDTO mapToPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getCreatedAt());
    }
}
