package com.byoungju94.blog.domain.content.service;

import com.byoungju94.blog.domain.content.Content;
import com.byoungju94.blog.domain.content.ContentState;
import com.byoungju94.blog.domain.content.dto.ContentCreateDTO;
import com.byoungju94.blog.domain.content.dto.ContentDTO;
import com.byoungju94.blog.domain.content.dto.ContentUpdateDTO;
import com.byoungju94.blog.domain.content.repository.ContentRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<ContentDTO> getAll(String postId) {
        return this.repository.findByPostId(postId);
    }

    @Transactional
    public Content createContent(ContentCreateDTO dto) {
         var content = Content.builder()
                .uuid(UUID.randomUUID().toString())
                .filePath(dto.filePath())
                .state(ContentState.OPENED)
                .orderNum(dto.orderNum())
                .createdAt(Instant.now())
                .postId(AggregateReference.to(dto.postId()))
                .build();

         return this.repository.save(content);
    }

    @Transactional
    public Content updateContent(ContentUpdateDTO dto) {
        var content = Content.builder()
                .uuid(dto.uuid())
                .filePath(dto.filePath())
                .state(ContentState.OPENED)
                .orderNum(dto.orderNum())
                .createdAt(Instant.now())
                .postId(AggregateReference.to(dto.postId()))
                .build();

        return this.repository.save(content);
    }

    @Transactional
    public Content deleteContent(String uuid) {
        var content = this.repository.findByLatestOfUuid(uuid);
        content.delete();
        return this.repository.save(content);
    }

}
