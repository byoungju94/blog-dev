package com.byoungju94.blog.domain.content.repository;

import java.util.List;

import com.byoungju94.blog.domain.content.Content;
import com.byoungju94.blog.domain.content.dto.ContentEventDTO;

public interface ContentReadRepository {

    List<ContentEventDTO> findByPostId(String postId);

    ContentEventDTO findByIdLatestEvent(String id);
}
