package com.byoungju94.blog.comment.repository;

import com.byoungju94.blog.account.repository.AccountNativeQuerySQL;

public final class CommentNativeQuerySQL {

    private CommentNativeQuerySQL() {}

    public static final String FIND_BY_POST_ID_WITH_PAGING = """
            SELECT c1.*, a1.name
            FROM TBL_COMMENT as c1
                INNER JOIN (SELECT MAX(seq) as seq FROM TBL_COMMENT GROUP BY id) as c2 ON c1.seq = c2.seq
                INNER JOIN (%s) as a1 ON a1.id = c1.account_id
            WHERE post_id = :post_id
                AND state = 'OPENED'
                AND id > 0
            LIMIT :start_page, :amount
            """.formatted(AccountNativeQuerySQL.findAllStateActive);
}
