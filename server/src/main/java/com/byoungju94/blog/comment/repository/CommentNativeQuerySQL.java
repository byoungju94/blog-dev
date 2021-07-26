package com.byoungju94.blog.comment.repository;

public final class CommentNativeQuerySQL {

    private CommentNativeQuerySQL() {}

    public static final String FIND_BY_POST_ID_WITH_PAGING = """
            SELECT *
            FROM tbl_comment as c1
                INNER JOIN (SELECT MAX(id) as id FROM tbl_comment GROUP BY uuid) as c2 ON c1.id = c2.id
                INNER JOIN (SELECT uuid, FROM tbl_account GROUP BY uuid, username) ON a.
            WHERE post_id = :post_id
                AND state = 'OPENED'
                AND id > 0
            """;
}
