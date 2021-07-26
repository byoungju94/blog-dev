CREATE TABLE tbl_post (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    state VARCHAR(30) CHECK (state in ('OPENED', 'BLOCKED', 'HIDDEN', 'DELETED')),
    account_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE tbl_comment (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    state VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    post_id VARCHAR(255) NOT NULL
);

CREATE TABLE tbl_account (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    state VARCHAR(30) CHECK (state in ('ACTIVE', 'LOCKED', 'DELETED'))
);
