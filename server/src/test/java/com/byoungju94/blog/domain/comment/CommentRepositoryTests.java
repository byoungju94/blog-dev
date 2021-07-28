package com.byoungju94.blog.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.byoungju94.blog.domain.account.Account;
import com.byoungju94.blog.domain.account.AccountState;
import com.byoungju94.blog.domain.account.repository.AccountRepository;
import com.byoungju94.blog.domain.category.Category;
import com.byoungju94.blog.domain.comment.repository.CommentRepository;
import com.byoungju94.blog.domain.post.Post;
import com.byoungju94.blog.domain.post.PostState;
import com.byoungju94.blog.domain.post.repository.PostRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class CommentRepositoryTests {

    @Autowired
    CommentRepository sut;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeAll
    void initialize() {
        accountRepository.save(account);
        postRepository.save(post);
    }

    @Test
    @Order(1)
    void create() {
        // given
        var comment = comments.get(0);

        // when
        var actual = this.sut.save(comment);

        // then
        assertEquals(comment, actual);
    }

    @Test
    @Order(2)
    void update() {
        // given
        var updateComment = Comment.builder()
                .id(commentId1.getId())
                .content("update my comment")
                .state(CommentState.UPDATED)
                .createdAt(Instant.now())
                .postId(postId)
                .accountId(accountId)
                .isNew(true)
                .build();

        // when
        this.sut.save(updateComment);
        var actual = this.sut.findByIdLatestEvent(commentId1.getId());

        // then
        assertEquals(commentId1.getId(), actual.id());
        assertEquals(CommentState.UPDATED.toString(), actual.state());
    }

    @Test
    @Order(3)
    void delete() {
        // given
        var oldComment = this.sut.findByIdLatestEvent(commentId1.getId());
        var deleteComment = Comment.builder()
                .id(oldComment.id())
                .content("DELETED")
                .state(CommentState.DELETED)
                .createdAt(Instant.now())
                .postId(postId)
                .accountId(accountId)
                .isNew(true)
                .build();

        // when
        this.sut.save(deleteComment);
        var deleteCommentDTO = this.sut.findByIdLatestEvent(deleteComment.getId());

        // then
        assertEquals(CommentState.DELETED.toString(), deleteCommentDTO.state());
        assertEquals(commentId1.getId(), deleteCommentDTO.id());
        assertEquals("DELETED", deleteCommentDTO.content());
    }

    @Test
    @Order(4)
    void findAll() {
        // given
        var pageable = PageRequest.of(0, 10);

        // when
        var commentDTOs = this.sut.findByPostIdWithPaging(postId.getId(), pageable);

        // then
        var findCommentDTO = commentDTOs
                .stream()
                .filter(commentDTO -> commentDTO.id().equals(commentId1.getId()))
                .collect(Collectors.toList());

        assertEquals(0, findCommentDTO.size());
    }
    
    private final AggregateReference<Account, String> accountId = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Post, String> postId = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Category, Long> categoryId = AggregateReference.to(1L);

    private final AggregateReference<Comment, String> commentId1 = AggregateReference.to(UUID.randomUUID().toString());
    private final AggregateReference<Comment, String> commentId2 = AggregateReference.to(UUID.randomUUID().toString());

    private final List<Comment> comments = List.of(
            Comment.builder()
                .id(commentId1.getId())
                .content("thanks for sharing us!")
                .state(CommentState.CREATED)
                .createdAt(Instant.now())
                .postId(postId)
                .accountId(accountId)
                .isNew(true)
                .build(),
            Comment.builder()
                .id(commentId2.getId())
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

    private final Post post = Post.builder()
            .id(postId.getId())
            .title("What is CQRS?")
            .state(PostState.OPENED)
            .createdAt(Instant.now())
            .categoryId(categoryId)
            .accountId(accountId)
            .isNew(true)
            .build();
}
