package com.byoungju94.blog.domain.post.repository;

public final class PostNativeQuerySQL {

    private PostNativeQuerySQL() {}

    public static final String findByCategoryId = """
            SELECT *
            FROM tbl_post as p1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_post GROUP BY id) as p2
                ON p1.seq = p2.seq
            WHERE p1.category_id = :categoryId 
                AND p1.state = :state
                AND p1.seq > 0
            """;

    public static final String findByIdLatestEvent = """
            SELECT *
            FROM tbl_post as p1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_post GROUP BY id) as p2
                ON p1.seq = p2.seq
            WHERE p1.id = :id
                AND p1.seq > 0
            """;


}
