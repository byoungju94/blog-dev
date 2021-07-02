package com.byoungju94.blog.comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.byoungju94.blog.account.Account;
import com.byoungju94.blog.post.Post;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Comment implements Persistable<UUID> {
    
    @Id
    private UUID id;

    @Version
    private long version;

    @Column("COMMENT_ID")
    private CommentContent content;

    private LocalDateTime createdAt;

    private AggregateReference<Post, UUID> postId;
    private AggregateReference<Account, UUID> writerId;

    @PersistenceConstructor
    public Comment(UUID id, 
                   CommentContent content, 
                   AggregateReference<Post, UUID> postId,
                   AggregateReference<Account, UUID> writerId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.writerId = writerId;
        this.isNew = false;
    }

    @Transient
    private Boolean isNew;
    
    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }    
}
