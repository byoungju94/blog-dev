package com.byoungju94.blog.account.repository;

public final class AccountNativeQuerySQL {

    private AccountNativeQuerySQL() { }
    
    static final String findAllStateActive = """
            SELECT *
            FROM tbl_account as a1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_account GROUP BY id) as a2
                ON a1.seq = a2.seq
            WHERE state = 'ACTIVE'
                AND a1.seq > 0
            """;

    static final String findById = """
            SELECT *
            FROM tbl_account as a1
                INNER JOIN (SELECT MAX(seq) as seq FROM tbl_account GROUP BY id) as a2
                ON a1.seq = a2.seq
            WHERE a1.id = :id
                AND a1.seq > 0
            """;
    
}
