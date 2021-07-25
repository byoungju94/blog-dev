package com.byoungju94.blog.content.repository;

import com.byoungju94.blog.content.Content;
import com.byoungju94.blog.content.dto.ContentDTO;

import java.util.List;
import java.util.Optional;

public interface ContentReadRepository {

    List<ContentDTO> findByPostId(String postId);

    Content findByLatestOfUuid(String uuid);
}
