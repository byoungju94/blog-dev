package com.byoungju94.blog.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.account.AccountState;
import com.byoungju94.blog.account.repository.AccountRepository;
import com.byoungju94.blog.comment.dto.CommentEventDTO;
import com.byoungju94.blog.comment.repository.CommentRepository;
import com.byoungju94.blog.post.Post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CommentRepositoryTests {

    private final AggregateReference<Account, String> accountId = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Post, String> postId = AggregateReference.to(UUID.randomUUID().toString());

    private final List<Comment> comments = List.of(
            Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("thanks for sharing us!")
                .state(CommentState.CREATED)
                .createdAt(Instant.now())
                .postId(postId)
                .accountId(accountId)
                .isNew(true)
                .build(),
            Comment.builder()
                .id(UUID.randomUUID().toString())
                .content("i think that's wrong...")
                .state(CommentState.UPDATED)
                .createdAt(Instant.now())
                .postId(postId)
                .accountId(accountId)
                .isNew(true)
                .build()
    );

    private final Account account = Account.builder()
            .id(accountId.getId())
            .username("byoungju94")
            .password("superPassword123")
            .name("Byoungju Park")
            .createdAt(Instant.now())
            .state(AccountState.ACTIVE)
            .isNew(true)
            .build();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository sut;

    @BeforeAll
    void initialize() {
        accountRepository.save(account);
    }

    @Test
    void create() {
        // given
        var comment = comments.get(0);

        // when
        var actual = this.sut.save(comment);

        // then
        assertEquals(comment, actual);
    }

    @Test
    void update() {
        // given
        var newComment = Comment.builder()
            .id(UUID.randomUUID())
            .content("update my comment")
            .state(CommentState.UPDATED)
            .createdAt(Instant.now())
            .postId(postId)
            .accountId(accountId)
            .isNew(true)
            .build();

        // when
        newComment = this.sut.save(newComment);

        sut.findAll().forEach(System.out::println);

        // then
        assertEquals(2L, sut.count());
        var comments = sut.findByPostIdWithPaging(postId.toString(), 0, 10);
        assertEquals(1, comments.size());
    }
    
}
