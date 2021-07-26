package com.byoungju94.blog.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.account.AccountRepository;
import com.byoungju94.blog.category.Category;
import com.byoungju94.blog.category.CategoryRepository;
import com.byoungju94.blog.post.repository.PostRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    private PostRepository sut;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    void initialize() {

    }

    @Test
    void insert() {
        // given
        Post post = posts.stream().findFirst().get();

        // when
        Post actual = this.sut.save(post);

        // then
        assertEquals(post, actual);
    }

    // @Test
    // void softDelete() {
    //     // given
    //     Post post = posts.stream().findFirst().get();
    //     post = this.sut.save(post);

    //     // when
    //     this.sut.delete(post);

    //     // then
    //     assertEquals(PostState.DELETED, post.getState());

    //     Optional<Post> load = this.sut.findById(post.getId());
    //     assertTrue(load.isPresent());
    //     assertEquals(PostState.DELETED, load.get().getState());

    //     Optional<Post> exclude = this.sut.findByIdExcludeDeleted(post.getId());
    //     assertEquals(Optional.empty(), exclude);
    // }

    private final AggregateReference<Category, UUID> categoryId = AggregateReference.to(UUID.randomUUID());
    private final AggregateReference<Account, UUID> accountId = AggregateReference.to(UUID.randomUUID());
    private final UUID postId1 = UUID.randomUUID();
    private final UUID postId2 = UUID.randomUUID();

    private final List<Content> contents = List.of(
        Content.builder()
            .id(UUID.randomUUID())
            .filePath("https://blog-dev-posts.byoungju94.me/describe_1.png")
            .orderNum("1")
            .extension(ContentExtension.PNG)
            .state(ContentState.OPEND)
            .isNew(true)
            .postId(AggregateReference.to(postId1))
            .build(),
        Content.builder()
            .id(UUID.randomUUID())
            .filePath("https://blog-dev-posts.byoungju94.me/dead_lock_example.txt")
            .orderNum("2")
            .extension(ContentExtension.TXT)
            .state(ContentState.OPEND)
            .isNew(true)
            .postId(AggregateReference.to(postId1))
            .build(),
        Content.builder()
            .id(UUID.randomUUID())
            .filePath("https://blog-dev-posts.byoungju94.me/describe_persistence_frameworks.mp4")
            .orderNum("2")
            .extension(ContentExtension.MP4)
            .state(ContentState.OPEND)
            .isNew(true)
            .postId(AggregateReference.to(postId1))
            .build()
    );

    private final List<Post> posts = List.of(
        Post.builder()
            .id(postId1)
            .title("What is aggregate root?")
            .state(PostState.OPEND)
            .accountId(this.accountId)
            .categoryId(categoryId)
            .contents(contents)
            .isNew(true)
            .build(),
        Post.builder()
            .id(postId2)
            .title("What is Event Sourcing")
            .state(PostState.OPEND)
            .accountId(this.accountId)
            .contents(contents)
            .isNew(true)
            .build()
    );
}
