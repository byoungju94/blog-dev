package com.byoungju94.blog.account.repository;

public final class AccountNativeQuerySQL {

    private AccountNativeQuerySQL() { }
    
    public static final String findAllStateActive = """
            SELECT *
            FROM TBL_ACCOUNT as a1
                INNER JOIN (SELECT MAX(seq) as seq FROM TBL_ACCOUNT GROUP BY id) as a2
                ON a1.seq = a2.seq
            WHERE state = 'ACTIVE'
            """;
    
}
