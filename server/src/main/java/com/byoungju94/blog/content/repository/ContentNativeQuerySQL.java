package com.byoungju94.blog.content.repository;

public class ContentNativeQuerySQL {

    public static String findByPostId = """
            SELECT *
            FROM tbl_content as c1
                INNER JOIN (SELECT MAX(id) as id FROM tbl_content GROUP BY uuid) as c2
                ON c1.id = c2.id
            WHERE post_id = :post_id AND state = :state
            """;

    public static String findByLatestOfUuid = """
            SELECT *
            FROM tbl_content
            GROUP BY uuid
            HAVING uuid = :uuid
            """;
}
