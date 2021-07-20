package com.byoungju94.blog.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.post.Post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
public class CommentRepositoryTests {

    @Autowired
    private CommentRepository sut;

    @Test
    void insert() {
        // given
        Comment comment = this.comments.stream().findFirst().get();

        // when
        Comment actual = this.sut.save(comment);

        // then
        assertEquals(1L, actual.getVersion());
        assertEquals(comment.getContent(), actual.getContent());

        Optional<Comment> load = this.sut.findById(comment.getId());
        assertTrue(load.isPresent());
        assertEquals(comment.getCreatedAt(), load.get().getCreatedAt());
        assertNotNull(load.get().getContent());
        assertEquals(comment.getContent().getBody(), load.get().getContent().getBody());
        assertEquals(comment.getContent().getMimeType(), load.get().getContent().getMimeType());        
    }
    
    private final AggregateReference<Post, UUID> postId = AggregateReference.to(UUID.randomUUID());
    private final AggregateReference<Account, UUID> writerId = AggregateReference.to(UUID.randomUUID());
    private final List<Comment> comments = List.of(
        Comment.builder()
            .id(UUID.randomUUID())
            .content(
                CommentContent.builder()
                    .body("you're wrong. try again!")
                    .mimeType("text/plain")
                    .build()
            )
            .postId(postId)
            .writerId(writerId)
            .createdAt(LocalDateTime.now().withNano(0))
            .isNew(true)
            .build(),
        Comment.builder()
            .id(UUID.randomUUID())
            .content(
                CommentContent.builder()
                    .body("thank you. keep posting please...")
                    .mimeType("text/plain")
                    .build()
            )
            .postId(postId)
            .writerId(writerId)
            .createdAt(LocalDateTime.now().withNano(0))
            .isNew(true)
            .build()
    );
}
