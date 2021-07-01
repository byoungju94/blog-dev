package com.byoungju94.blog.label;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.byoungju94.blog.post.Post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
public class LabelRepositoryTests {

    @Autowired
    private LabelRepository sut;

    @Test
    void insert() {
        // given
        Label label = this.labels.stream().findFirst().get();

        // when
        Label actual = this.sut.save(label);

        // then
        assertEquals(label, actual);
        assertFalse(label.isNew());

        Optional<Label> load = this.sut.findById(label.getId());
        assertFalse(load.get().isNew());
        assertEquals(this.postId, load.get().getPostId());
    }

    private final AggregateReference<Post, UUID> postId = AggregateReference.to(UUID.randomUUID());
    private final List<Label> labels = List.of(
        Label.builder()
            .id(UUID.randomUUID())
            .name("architecture")
            .color("blue")
            .postId(postId)
            .build(),
        Label.builder()
            .id(UUID.randomUUID())
            .name("DDD")
            .color("red")
            .postId(postId)
            .build()
    );    
}
