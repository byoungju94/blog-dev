package com.byoungju94.blog.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    private final List<Post> posts =  List.of(
        Post.builder()
            .id(UUID.randomUUID())
            .title("What is aggregate root?")
            .authorId(1L)
            .contentFilePath("s3://aggregate_root.markdown")
            .isInsert(true)
            .build(),
        
        Post.builder()
            .id(UUID.randomUUID())
            .title("What is Event Sourcing")
            .authorId(2L)
            .contentFilePath("s3://event_sourcing.markdown")
            .isInsert(true)
            .build()
    );
    
}
