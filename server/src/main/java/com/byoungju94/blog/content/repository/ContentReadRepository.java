package com.byoungju94.blog.content.repository;

import java.util.List;

import com.byoungju94.blog.content.Content;
import com.byoungju94.blog.content.dto.ContentDTO;

public interface ContentReadRepository {

    List<ContentDTO> findByPostId(String postId);

    Content findByLatestOfUuid(String uuid);
}