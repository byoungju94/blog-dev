package com.byoungju94.blog.domain.content.repository;

public final class ContentNativeQuerySQL {

    private ContentNativeQuerySQL() {}

    public static final String findByPostId = """
            SELECT *
            FROM tbl_content as c1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_content GROUP BY id) as seq
                ON c1.seq = c2.seq
            WHERE post_id = :postId 
                AND state = :state
                AND c1.seq > 0
            """;

    public static final String findByIdLatestEvent = """
            SELECT *
            FROM tbl_content as c1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_content GROUP BY id) as c2
                ON c1.seq = c2.seq
            WHERE c1.seq = :id
                AND c1.seq > 0
            """;
}
