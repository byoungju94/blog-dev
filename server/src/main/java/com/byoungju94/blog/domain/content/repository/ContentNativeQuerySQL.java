package com.byoungju94.blog.domain.content.repository;

public final class ContentNativeQuerySQL {

    private ContentNativeQuerySQL() {}

    public static final String findByPostId = """
            SELECT *
            FROM tbl_content as c1
                INNER JOIN (SELECT MAX(id) as id FROM tbl_content GROUP BY uuid) as c2
                ON c1.id = c2.id
            WHERE post_id = :post_id AND state = :state
            """;

    public static final String findByLatestOfUuid = """
            SELECT *
            FROM tbl_content
            GROUP BY uuid
            HAVING uuid = :uuid
            """;
}
