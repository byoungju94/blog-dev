package com.byoungju94.blog.controller;

import com.byoungju94.blog.controller.exception.ResourceNotFoundException;
import com.byoungju94.blog.domain.post.dto.PostDTO;
import com.byoungju94.blog.domain.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all/{categoryId}")
    public List<PostDTO> retrieveAll(@PathVariable Long categoryId) {
        var pageable = PageRequest.of(0, 10);
        return this.repository.findByCategoryIdWithPaging(categoryId, pageable);
    }

    @GetMapping("/{id}")
    public EntityModel<PostDTO> retrieve(@PathVariable String id) {
        var postDTO = this.repository.findByIdLatestEvent(id);

        if (postDTO == null)
            throw new ResourceNotFoundException("id: " + id);

        var model = EntityModel.of(postDTO);
        var linkToPost = linkTo(methodOn(this.getClass()).retrieveAll(Long.valueOf(postDTO.categoryId())));
        model.add(linkToPost.withRel("all-posts"));

        return model;
    }
}
