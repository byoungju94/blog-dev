package com.byoungju94.blog.domain.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.byoungju94.blog.domain.account.Account;
import com.byoungju94.blog.domain.account.AccountState;
import com.byoungju94.blog.domain.account.repository.AccountRepository;
import com.byoungju94.blog.domain.category.Category;
import com.byoungju94.blog.domain.category.CategoryRepository;
import com.byoungju94.blog.domain.content.dto.ContentEventDTO;
import com.byoungju94.blog.domain.content.repository.ContentRepository;
import com.byoungju94.blog.domain.post.Post;
import com.byoungju94.blog.domain.post.PostState;
import com.byoungju94.blog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContentRepositoryTests {

    @Autowired
    ContentRepository sut;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeAll
    void initialize() {
        accountRepository.save(this.account);

        var category = categoryRepository.save(this.category);
        this.categoryId = AggregateReference.to(category.getId());

        var post = Post.builder()
                .id(postId.getId())
                .title("What is CQRS?")
                .state(PostState.OPENED)
                .createdAt(Instant.now())
                .categoryId(this.categoryId)
                .accountId(this.accountId)
                .isNew(true)
                .build();

        postRepository.save(post);
    }

    @Test
    @Order(1)
    void create() {
        // given
        var content1 = this.contents.get(0);
        var content2 = this.contents.get(1);

        // when
        var actual1 = sut.save(content1);
        var actual2 = sut.save(content2);

        // then
        assertEquals(content1, actual1);
        assertEquals(content2, actual2);
    }

    private final AggregateReference<Content, String> contentId1 = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Content, String> contentId2 = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Post, String> postId = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Account, String> accountId = AggregateReference.to(UUID.randomUUID().toString());
    private AggregateReference<Category, Long> categoryId = null;

    private final List<Content> contents = List.of(
            Content.builder()
                .id(contentId1.getId())
                .filePath("s3://blog_dev_posts/java/stream_api")
                .state(ContentState.OPENED)
                .orderNum(1L)
                .createdAt(Instant.now())
                .postId(postId)
                .isNew(true)
                .build(),
            Content.builder()
                .id(contentId2.getId())
                .filePath("s3://blog_dev_posts/algorithm/merge_sort")
                .state(ContentState.OPENED)
                .orderNum(2L)
                .createdAt(Instant.now())
                .postId(postId)
                .isNew(true)
                .build()
    );

    private final Category category = Category.builder()
            .name("java")
            .isNew(true)
            .build();

    private final Account account = Account.builder()
            .id(this.accountId.getId())
            .username("byoungju94")
            .password("superPassword123")
            .name("Byoungju Park")
            .createdAt(Instant.now())
            .state(AccountState.ACTIVE)
            .isNew(true)
            .build();
}
