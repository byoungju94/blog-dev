package com.byoungju94.blog.comment.repository;

public final class CommentNativeQuerySQL {

    private CommentNativeQuerySQL() {}

    static final String findByPostIdWithPaging = """
            SELECT c.*, a.name FROM tbl_comment as c
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_comment GROUP BY id) as c2 ON c.seq = c2.seq
                LEFT JOIN tbl_post as p ON c.post_id = p.id
                LEFT JOIN tbl_account as a ON c.account_id = a.id
            WHERE c.post_id = :postId
                AND c.state != 'DELETED'
                AND c.seq > 0
            """;

    static final String findByIdLatestEvent = """
            SELECT *
            FROM tbl_comment as c1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_comment GROUP BY id) as c2 
                ON c1.seq = c2.seq
            WHERE c1.id = :id
                AND c1.seq > 0
            """;
}
