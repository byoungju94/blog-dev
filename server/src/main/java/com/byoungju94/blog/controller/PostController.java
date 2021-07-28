package com.byoungju94.blog.controller;

import com.byoungju94.blog.controller.exception.ResourceNotFoundException;
import com.byoungju94.blog.domain.post.Post;
import com.byoungju94.blog.domain.post.dto.PostDTO;
import com.byoungju94.blog.domain.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController("/api/v1/post")
public class PostController {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all/{categoryId}")
    public List<PostDTO> retrieveAll(@PathVariable String categoryId) {
        var pageable = PageRequest.of(0, 10);
        return this.repository.findByCategoryIdWithPaging(categoryId, pageable);
    }

    @GetMapping("/{id}")
    public EntityModel<PostDTO> retrieve(@PathVariable String id) {
        var postDTO = this.repository.findByIdLatestEvent(id);

        if (postDTO == null)
            throw new ResourceNotFoundException("id: " + id);

        var model = EntityModel.of(postDTO);

        linkTo(methodOn(this.getClass()).retrieveAll(postDTO.categoryId()));

        return model;
    }


}
