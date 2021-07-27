package com.byoungju94.blog.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.account.AccountState;
import com.byoungju94.blog.account.repository.AccountRepository;
import com.byoungju94.blog.category.Category;
import com.byoungju94.blog.category.CategoryRepository;
import com.byoungju94.blog.post.repository.PostRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class PostRepositoryTests {

    @Autowired
    PostRepository sut;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeAll
    void initialize() {
        accountRepository.save(account);
        this.categoryId = categoryRepository.save(category).getId();        
    }

    @AfterAll
    void cleanUp() {
        accountRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    void create() {
        // given
        postId1 = UUID.randomUUID().toString();
        postId2 = UUID.randomUUID().toString();

        var posts = List.of(
            Post.builder()
                .id(postId1)
                .title("What is CQRS?")
                .state(PostState.OPENED)
                .createdAt(Instant.now())
                .categoryId(AggregateReference.to(categoryId))
                .accountId(AggregateReference.to(accountId))
                .isNew(true)
                .build(),
            Post.builder()
                .id(postId2)
                .title("What is Event Sourcing?")
                .state(PostState.OPENED)
                .createdAt(Instant.now())
                .categoryId(AggregateReference.to(categoryId))
                .accountId(AggregateReference.to(accountId))
                .isNew(true)
                .build()
        );

        // when
        var actual1 = sut.save(posts.get(0));
        var actual2 = sut.save(posts.get(1));

        // then
        assertEquals(posts.get(0), actual1);
        assertEquals(posts.get(1), actual2);
    }

    @Test
    @Order(2)
    void findByIdLatestEvent() {
        // given
        var id = postId1;

        // when
        var actual = sut.findByIdLatestEvent(id);

        System.out.println("actual state: " + actual.state().toString());

        // then
        assertEquals(postId1, actual.id());
        assertEquals("What is CQRS?", actual.title());
        assertEquals(PostState.OPENED.toString(), actual.state().toString());
    }

    @Test
    @Order(3)
    void delete() {
        // given
        var post = Post.builder()
                .id(postId2)
                .title("What is Event Sourcing?")
                .state(PostState.DELETED)
                .createdAt(Instant.now())
                .categoryId(AggregateReference.to(categoryId))
                .accountId(AggregateReference.to(accountId))
                .isNew(true)
                .build();

        // when
        sut.save(post);
        var actual = sut.findByIdLatestEvent(post.getId());

        // then
        assertEquals(postId2, actual.id());
        assertEquals(PostState.DELETED.toString(), actual.state().toString());
        assertEquals("What is Event Sourcing?", actual.title());
    }

    @Test
    @Order(4)
    void findByCategoryIdWithPaging() {
        //given
        var paging = PageRequest.of(0, 10);
        var posts = sut.findByCategoryIdWithPaging(categoryId, paging);
        
        assertEquals(1, posts.size());
    }

    private String postId1 = null;
    private String postId2 = null;

    private Long categoryId = null;
    private final String accountId = UUID.randomUUID().toString();

    private final Account account = Account.builder()
            .id(accountId)
            .username("byoungju94")
            .password("superPassword123")
            .name("Byoungju Park")
            .createdAt(Instant.now())
            .state(AccountState.ACTIVE)
            .isNew(true)
            .build();

    private final Category category = Category.builder()
            .name("Java")
            .isNew(true)
            .build();

    // @Test
    // void insert() {
    //     // given
    //     Post post = posts.stream().findFirst().get();

    //     // when
    //     Post actual = this.sut.save(post);

    //     // then
    //     assertEquals(post, actual);
    // }

    // // @Test
    // // void softDelete() {
    // //     // given
    // //     Post post = posts.stream().findFirst().get();
    // //     post = this.sut.save(post);

    // //     // when
    // //     this.sut.delete(post);

    // //     // then
    // //     assertEquals(PostState.DELETED, post.getState());

    // //     Optional<Post> load = this.sut.findById(post.getId());
    // //     assertTrue(load.isPresent());
    // //     assertEquals(PostState.DELETED, load.get().getState());

    // //     Optional<Post> exclude = this.sut.findByIdExcludeDeleted(post.getId());
    // //     assertEquals(Optional.empty(), exclude);
    // // }

    // private final AggregateReference<Category, UUID> categoryId = AggregateReference.to(UUID.randomUUID());
    // private final AggregateReference<Account, UUID> accountId = AggregateReference.to(UUID.randomUUID());
    // private final UUID postId1 = UUID.randomUUID();
    // private final UUID postId2 = UUID.randomUUID();

    // private final List<Content> contents = List.of(
    //     Content.builder()
    //         .id(UUID.randomUUID())
    //         .filePath("https://blog-dev-posts.byoungju94.me/describe_1.png")
    //         .orderNum("1")
    //         .extension(ContentExtension.PNG)
    //         .state(ContentState.OPEND)
    //         .isNew(true)
    //         .postId(AggregateReference.to(postId1))
    //         .build(),
    //     Content.builder()
    //         .id(UUID.randomUUID())
    //         .filePath("https://blog-dev-posts.byoungju94.me/dead_lock_example.txt")
    //         .orderNum("2")
    //         .extension(ContentExtension.TXT)
    //         .state(ContentState.OPEND)
    //         .isNew(true)
    //         .postId(AggregateReference.to(postId1))
    //         .build(),
    //     Content.builder()
    //         .id(UUID.randomUUID())
    //         .filePath("https://blog-dev-posts.byoungju94.me/describe_persistence_frameworks.mp4")
    //         .orderNum("2")
    //         .extension(ContentExtension.MP4)
    //         .state(ContentState.OPEND)
    //         .isNew(true)
    //         .postId(AggregateReference.to(postId1))
    //         .build()
    // );

    // private final List<Post> posts = List.of(
    //     Post.builder()
    //         .id(postId1)
    //         .title("What is aggregate root?")
    //         .state(PostState.OPEND)
    //         .accountId(this.accountId)
    //         .categoryId(categoryId)
    //         .contents(contents)
    //         .isNew(true)
    //         .build(),
    //     Post.builder()
    //         .id(postId2)
    //         .title("What is Event Sourcing")
    //         .state(PostState.OPEND)
    //         .accountId(this.accountId)
    //         .contents(contents)
    //         .isNew(true)
    //         .build()
    // );
}
