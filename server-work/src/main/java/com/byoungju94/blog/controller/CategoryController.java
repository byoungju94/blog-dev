package com.byoungju94.blog.controller;

import com.byoungju94.blog.controller.exception.ResourceNotFoundException;
import com.byoungju94.blog.domain.category.Category;
import com.byoungju94.blog.domain.category.CategoryRepository;
import com.byoungju94.blog.domain.category.dto.CategoryCreateDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public EntityModel<Iterable<Category>> retrieveAll() {
        var categories = this.repository.findAll();
        var categoryRandomId = StreamSupport.stream(categories.spliterator(), false)
                .map(category -> category.getId())
                .findFirst().get();

        var model = EntityModel.of(categories);
        var linkToRetrieve = linkTo(methodOn(this.getClass()).retrieve(categoryRandomId));
        model.add(linkToRetrieve.withRel("retrieve-category"));
        return model;
    }

    @GetMapping("/{id}")
    public EntityModel<Category> retrieve(@PathVariable Long id) {
        var category = this.repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("id" + id));

        var model = EntityModel.of(category);
        var linkToRetrieveAllPost = linkTo(methodOn(PostController.class).retrieveAll(category.getId()));
        model.add(linkToRetrieveAllPost.withRel("retrieve-post-all"));
        return model;
    }

    @PostMapping("")
    public EntityModel<Category> create(@RequestBody CategoryCreateDTO dto) {
        var category = Category.builder()
                .name(dto.name())
                .build();

        category = this.repository.save(category);

        var model = EntityModel.of(category);
        var linkToRetrieve = linkTo(methodOn(this.getClass()).retrieve(category.getId()));
        var linkToRetrieveAll = linkTo(methodOn(this.getClass()).retrieveAll());
        model.add(linkToRetrieve.withRel("retrieve-category"));
        model.add(linkToRetrieveAll.withRel("retrieve-category-all"));
        return model;
    }
}
