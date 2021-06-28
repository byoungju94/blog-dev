package com.byoungju94.blog.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.byoungju94.blog.account.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    private PostRepository sut;

    @Test
    void insert() {
        // given
        Post post = posts.stream().findFirst().get();

        // when
        Post actual = this.sut.save(post);

        // then
        assertEquals(post, actual);
    }

    @Test
    void softDelete() {
        // given
        Post post = posts.stream().findFirst().get();
        post = this.sut.save(post);

        // when
        this.sut.delete(post);

        // then
        assertEquals(PostState.DELETED, post.getState());

        Optional<Post> load = this.sut.findById(post.getId());
        assertTrue(load.isPresent());
        assertEquals(PostState.DELETED, load.get().getState());

        Optional<Post> exclude = this.sut.findByIdExcludeDeleted(post.getId());
        assertEquals(Optional.empty(), exclude);
    }

    private final AggregateReference<Account, UUID> authorId = AggregateReference.to(UUID.randomUUID());

    private final List<Post> posts =  List.of(
        Post.builder()
            .id(UUID.randomUUID())
            .title("What is aggregate root?")
            .authorId(this.authorId)
            .contentFilePath("s3://aggregate_root.markdown")
            .isInsert(true)
            .build(),
        
        Post.builder()
            .id(UUID.randomUUID())
            .title("What is Event Sourcing")
            .authorId(this.authorId)
            .contentFilePath("s3://event_sourcing.markdown")
            .isInsert(true)
            .build()
    );
    
}
